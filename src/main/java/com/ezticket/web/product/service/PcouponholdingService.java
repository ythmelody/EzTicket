package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PcouponholdingDTO;
import com.ezticket.web.product.pojo.Pcouponholding;
import com.ezticket.web.product.repository.PcouponholdingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PcouponholdingService {
    @Autowired
    private PcouponholdingRepository pcouponholdingRepository;

    @Autowired
    private ModelMapper modelMapper;

//    public List<PcouponholdingDTO> getPcouponHoldingByMemberno(Integer memberno){
//        return pcouponholdingRepository.findByMemberno(memberno)
//                .stream()
//                .map(this::EntityToDTO)
//                .collect(Collectors.toList());
//    }
    public PcouponholdingDTO getPcouponHoldingByID(Integer id) {
        Pcouponholding pcouponholding = pcouponholdingRepository.getReferenceById(id);
        return EntityToDTO(pcouponholding);
    }
    public PcouponholdingDTO EntityToDTO(Pcouponholding pcouponholding){
        return modelMapper.map(pcouponholding, PcouponholdingDTO.class);
    }
    public List<PcouponholdingDTO> getAllPcouponHolding(){
        return pcouponholdingRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

}
