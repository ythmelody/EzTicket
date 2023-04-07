package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.AddPorderDTO;
import com.ezticket.web.product.dto.OrderProductDTO;
import com.ezticket.web.product.dto.PorderDTO;
import com.ezticket.web.product.dto.PorderDetailsDTO;
import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.PdetailsPK;
import com.ezticket.web.product.pojo.Porder;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.repository.PdetailsRepository;
import com.ezticket.web.product.repository.PorderRepository;
import com.ezticket.web.product.repository.ProductDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ModelMapper modelMapper;

    // GetPordersByID
    public List<PorderDTO> getPordersByID(Integer id){
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
    public PorderDetailsDTO EntityToDetailDTO(Porder porder){
        PorderDetailsDTO porderDetailsDTO = modelMapper.map(porder, PorderDetailsDTO.class);
        porderDetailsDTO.setProducts(porder.getProducts());
        return porderDetailsDTO;
    }

    // UpdatePorderByID
    public PorderDTO updateByID(Integer id, Integer processStatus) {
        Porder porder = porderRepository.getReferenceById(id);
        porder.setPprocessstatus(processStatus);
        Porder updatedPorder = porderRepository.save(porder);
        return EntityToDTO(updatedPorder);
    }

    public PorderDTO EntityToDTO(Porder porder){
        return modelMapper.map(porder, PorderDTO.class);
    }
    public List<PorderDTO> getAllPorder(){
    return porderRepository.findAll()
            .stream()
            .map(this::EntityToDTO)
            .collect(Collectors.toList());
    }

    // AddPorder
    @Transactional
    public PorderDTO AddPorder(AddPorderDTO addPorderDTO) {
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
            pdetailsRepository.save(pdetails);
            Product product = dao.getByPrimaryKey(orderProducts.get(i).getProductno());
            product.setPqty(product.getPqty()-orderProducts.get(i).getQuantity());
            dao.update(product);
        }
        return EntityToDTO(porder);
    }
}
