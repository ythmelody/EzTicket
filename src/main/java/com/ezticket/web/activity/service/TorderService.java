package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.AddTorderDTO;
import com.ezticket.web.activity.dto.OrderTicketDTO;
import com.ezticket.web.activity.dto.TorderDto;
import com.ezticket.web.activity.pojo.Tdetails;
import com.ezticket.web.activity.pojo.Torder;
import com.ezticket.web.activity.repository.TdetailsRepository;
import com.ezticket.web.activity.repository.TorderRepository;
import com.ezticket.web.product.dto.AddPorderDTO;
import com.ezticket.web.product.dto.OrderProductDTO;
import com.ezticket.web.product.dto.PorderDTO;
import com.ezticket.web.product.pojo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TorderService {
    @Autowired
    private TorderRepository torderRepository;
    @Autowired
    private ModelMapper modelMapper;

    //    Add by Shawn on 04/17
    @Autowired
    private TdetailsRepository tdetailsRepository;

    @Autowired
    private SeatsService seatsService;

    @Autowired
    private SessionService sessionService;

    public List<TorderDto> findByOrderByTorderNoDesc() {
        return torderRepository.findByOrderByTorderNoDesc()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TorderDto> findById(Integer torderNo) {
        return torderRepository.findById(torderNo).map(this::entityToDTO);
    }

    private TorderDto entityToDTO(Torder torder) {
        TorderDto torderDto = new TorderDto();
        torderDto = modelMapper.map(torder, TorderDto.class);
        return torderDto;
    }

    // AddPorder
    @Transactional
    public TorderDto addTOrder(AddTorderDTO addTorderDTO) {
        Torder torder = new Torder();
        torder.setMemberNo(addTorderDTO.getMemberno());
        torder.setTtotal(addTorderDTO.getTtotal());
        torder.setTcheckTotal(addTorderDTO.getTcheckTotal());
        torder.setTorderDate(Timestamp.valueOf(LocalDateTime.now()));
        torder.setTpaymentStatus(0);
        torder.setTprocessStatus(0);

        Torder savedTorder = torderRepository.save(torder);

        List<OrderTicketDTO> orderTickets = addTorderDTO.getOrderTickets();

        for (int i = 0; i < orderTickets.size(); i++) {
            Tdetails tdetails = new Tdetails();
            tdetails.setTorderNo(savedTorder.getTorderNo());
            tdetails.setSessionNo(orderTickets.get(i).getSessionNo());
            tdetails.setSeatNo(orderTickets.get(i).getSeatNo());
            tdetails.setTqty(orderTickets.get(i).getTQty());
            tdetails.setAcommentStatus(0);
            tdetailsRepository.save(tdetails);

            if (orderTickets.get(i).getSeatNo() != null) {
                // 訂單建立後，如節目屬於有座位的，則先將該座位的編號從 Redis 中移除，並改變 MySQL 座位狀態為已售出
                seatsService.setSessionSeats(2, orderTickets.get(i).getSeatNo(), orderTickets.get(i).getSessionNo());
            } else {
                // 訂單建立後，如節目屬於無座位的，則改變 MySQL 可出售票券數量
                sessionService.updateTicketQTY(orderTickets.get(i).getTQty(), orderTickets.get(i).getSessionNo());
            }
        }

        return entityToDTO(torder);
    }

    public Torder getById(Integer torderNo){
        return torderRepository.getReferenceById(torderNo);
    }

    public Torder updateTorder(Torder torder){
        return torderRepository.save(torder);
    }


}
