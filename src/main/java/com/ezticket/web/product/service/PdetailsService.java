package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PcouponStatusDTO;
import com.ezticket.web.product.dto.PdetailsDTO;
import com.ezticket.web.product.dto.PdetailsStatusDTO;
import com.ezticket.web.product.pojo.Pcoupon;
import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.PdetailsPK;
import com.ezticket.web.product.repository.PdetailsRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class PdetailsService {
    @Autowired
    private PdetailsRepository pdetailsRepository;

    @Autowired
    private ModelMapper modelMapper;


    public PdetailsDTO EntityToDTO(Pdetails pdetails){
        return modelMapper.map(pdetails, PdetailsDTO.class);
    }
    public List<PdetailsDTO> getAllPdetails(){
        return pdetailsRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public List<PdetailsDTO> getPdetailsByPorderno(Integer porderno){
        return pdetailsRepository.findByPorderno(porderno)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    //更新訂單明細評論狀態
    public PdetailsStatusDTO updateByID(PdetailsPK pdetailsNo, Integer pcommentstatus) {
        Pdetails pdetails = pdetailsRepository.getReferenceById(pdetailsNo);
        pdetails.setPcommentstatus(pcommentstatus);
        Pdetails updatedPdetails = pdetailsRepository.save(pdetails);
        return EntityToStatusDTO(updatedPdetails);
    }

    //更新訂單明細評論狀態
    public PdetailsStatusDTO EntityToStatusDTO(Pdetails pdetails){
        return modelMapper.map(pdetails, PdetailsStatusDTO.class);
    }

}
