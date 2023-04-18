package com.ezticket.web.product.service;

import com.ezticket.ecpay.service.OrderService;
import com.ezticket.web.product.dto.*;
import com.ezticket.web.product.pojo.*;
import com.ezticket.web.product.repository.PcouponholdingRepository;
import com.ezticket.web.product.repository.PdetailsRepository;
import com.ezticket.web.product.repository.PorderRepository;
import com.ezticket.web.product.repository.ProductDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PorderService {

    @Autowired
    private ProductDAO dao;
    @Autowired
    private PorderRepository porderRepository;
    @Autowired
    private PdetailsRepository pdetailsRepository;
    @Autowired
    private PcouponholdingRepository pcouponholdingRepository;
    @Autowired
    private OrderService ecpayorderService;
    @Autowired
    private ModelMapper modelMapper;

    // GetPordersByID
    public List<PorderDTO> getPordersByID(Integer id) {
        return porderRepository.findByID(id)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    public PorderDTO getPorderByID(Integer id) {
        Porder porder = porderRepository.getReferenceById(id);
        return EntityToDTO(porder);
    }

    // GetPorderDetailsByID
    public PorderDetailsDTO getPorderDetailsByID(Integer id) {
        Porder porder = porderRepository.findByPorderno(id);
        return EntityToDetailDTO(porder);
    }

    public PorderDetailsDTO EntityToDetailDTO(Porder porder) {
        PorderDetailsDTO porderDetailsDTO = modelMapper.map(porder, PorderDetailsDTO.class);
        porderDetailsDTO.setProducts(porder.getProducts());
        return porderDetailsDTO;
    }

    // UpdatePorderByID
    @Transactional
    public PorderDTO updateByID(Integer id, Integer processStatus) {
        Porder porder = porderRepository.getReferenceById(id);
        // 付款狀態 Ppaymentstatus 0 未付款 1 已付款 2 已退款
        // 訂單狀態 Pprocessstatus 0 未處理 1 配送中 2 已結案 3 取消
        if (porder.getPpaymentstatus() == 0 && processStatus != 3) {
            throw new IllegalStateException("未付款的訂單只能取消");
        }
        if (porder.getPprocessstatus() == 2 && processStatus == 3) {
            throw new IllegalStateException("已結案的訂單不能取消");
        }
        switch (processStatus) {
            case 1:
                porder.setPshipdate(LocalDateTime.now());
                break;
            case 2:
                porder.setParrivedate(LocalDateTime.now());
                porder.setPclosedate(LocalDateTime.now());
                break;
            case 3:
                porder.setPclosedate(LocalDateTime.now());
                List<Pdetails> pdetails = pdetailsRepository.findByPorderno(porder.getPorderno());
                for (Pdetails pdetail : pdetails) {
                    Product product = dao.getByPrimaryKey(pdetail.getPdetailsNo().getProductno());
                    product.setPqty(product.getPqty() + pdetail.getPorderqty());
                    pdetailsRepository.save(pdetail);
                    dao.update(product);
                }
                break;
        }
        porder.setPprocessstatus(processStatus);
        Porder updatedPorder = porderRepository.save(porder);
        return EntityToDTO(updatedPorder);
    }

    public PorderDTO EntityToDTO(Porder porder) {
        return modelMapper.map(porder, PorderDTO.class);
    }

    public List<PorderDTO> getAllPorder() {
        return porderRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    // AddPorder
    @Transactional
    public PorderDTO addPorder(AddPorderDTO addPorderDTO) {
        Porder porder = new Porder();
        porder.setMemberno(addPorderDTO.getMemberno());
        porder.setPtotal(addPorderDTO.getPtotal());
        porder.setPdiscounttotal(addPorderDTO.getPdiscounttotal());
        porder.setPcoupontotal(addPorderDTO.getPcoupontotal());
        porder.setPchecktotal(addPorderDTO.getPchecktotal());
        porder.setPcouponno(addPorderDTO.getPcouponno());
        porder.setRecipient(addPorderDTO.getRecipient());
        porder.setRephone(addPorderDTO.getRephone());
        porder.setReaddress(addPorderDTO.getReaddress());
        porder.setPorderdate(LocalDateTime.now());
        porder.setPpaymentstatus(0);
        porder.setPprocessstatus(0);
        if (addPorderDTO.getPcouponno() != null){
            PcouponholdingPK pcouponholdingPK = new PcouponholdingPK(addPorderDTO.getPcouponno(),addPorderDTO.getMemberno());
            Pcouponholding pcouponholding = pcouponholdingRepository.getReferenceById(pcouponholdingPK);
            pcouponholding.setPcouponstatus((byte) 1);
        }
        Porder porderno = porderRepository.save(porder);
        List<OrderProductDTO> orderProducts = addPorderDTO.getOrderProducts();
        for (int i = 0; i < orderProducts.size(); i++) {
            Pdetails pdetails = new Pdetails();
            PdetailsPK pdetailsPK = new PdetailsPK();
            pdetailsPK.setPorderno(porderno.getPorderno());
            pdetailsPK.setProductno(orderProducts.get(i).getProductno());
            pdetails.setPdetailsNo(pdetailsPK);
            pdetails.setPorderqty(orderProducts.get(i).getQuantity());
            pdetails.setPprice(orderProducts.get(i).getPprice());
            pdetails.setPcommentstatus(0);
            pdetailsRepository.save(pdetails);
            Product product = dao.getByPrimaryKey(orderProducts.get(i).getProductno());
            product.setPqty(product.getPqty() - orderProducts.get(i).getQuantity());
            dao.update(product);
        }
        return EntityToDTO(porder);
    }


    @Scheduled(cron = "0 0 * * * *")
    public void checkOrderPayStatus() {
        LocalDateTime today = LocalDateTime.now();
        List<Porder> porders = porderRepository.findAll();

        for (Porder porder : porders) {
            //  當付款狀態為0
            if (porder.getPpaymentstatus() == 0){
                LocalDateTime start = porder.getPorderdate();
                // 加上10分鐘
                LocalDateTime end = start.plusMinutes(10);
                byte status = 0;
                if (today.isEqual(start) || today.isEqual(end)) {
                    status = 1;
                } else if (today.isAfter(start) && today.isBefore(end)) {
                    status = 1;
                }
                porder.getPprocessstatus();
                porderRepository.save(porder);
            }
        }
    }

}
