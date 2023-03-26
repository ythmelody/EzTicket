package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PcouponDTO;
import com.ezticket.web.product.dto.PcouponholdingDTO;
import com.ezticket.web.product.pojo.Pcoupon;
import com.ezticket.web.product.pojo.Pcouponholding;
import com.ezticket.web.product.repository.PcouponRepository;
import com.ezticket.web.product.repository.PcouponholdingRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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


    public PcouponholdingDTO getPcouponHoldingByID(Integer id) {
        Pcouponholding pcouponholding = pcouponholdingRepository.getReferenceById(id);
        return EntityToDTO(pcouponholding);
    }
    public PcouponholdingDTO EntityToDTO(Pcouponholding pcouponholding){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PcouponholdingDTO pcouponholdingDTO = new PcouponholdingDTO();
        pcouponholdingDTO = modelMapper.map(pcouponholding, PcouponholdingDTO.class);

        return pcouponholdingDTO;
    }
    public List<PcouponholdingDTO> getAllPcouponHolding(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        return pcouponholdingRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

}
