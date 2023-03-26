package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PcouponDTO;
import com.ezticket.web.product.dto.PdetailsDTO;
import com.ezticket.web.product.dto.PorderDTO;
import com.ezticket.web.product.pojo.Pcoupon;
import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.Porder;
import com.ezticket.web.product.repository.PcouponRepository;
import com.ezticket.web.product.repository.PdetailsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PcouponService {
    @Autowired
    private PcouponRepository pcouponRepository;

    @Autowired
    private ModelMapper modelMapper;


    public PcouponDTO getPcouponByID(Integer id) {
        Pcoupon pcoupon = pcouponRepository.getReferenceById(id);
        return EntityToDTO(pcoupon);
    }
    public PcouponDTO EntityToDTO(Pcoupon pcoupon){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PcouponDTO pcouponDTO = new PcouponDTO();
        pcouponDTO = modelMapper.map(pcoupon, PcouponDTO.class);

        return pcouponDTO;
    }
    public List<PcouponDTO> getAllPcoupon(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        return pcouponRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

}
