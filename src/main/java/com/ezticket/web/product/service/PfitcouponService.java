package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PfitcouponDTO;
import com.ezticket.web.product.pojo.Pfitcoupon;
import com.ezticket.web.product.repository.PfitcouponRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PfitcouponService {

    @Autowired
    private PfitcouponRepository pfitcouponRepository;
    @Autowired
    private ModelMapper modelMapper;

    public PfitcouponDTO EntityToDTO(Pfitcoupon pfitcoupon){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PfitcouponDTO pfitcouponDTO = new PfitcouponDTO();
        pfitcouponDTO = modelMapper.map(pfitcoupon, PfitcouponDTO.class);
        return pfitcouponDTO;
    }
    public List<PfitcouponDTO> getAllPfitcoupon(){
    modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE);

    return pfitcouponRepository.findAll()
            .stream()
            .map(this::EntityToDTO)
            .collect(Collectors.toList());
}
}
