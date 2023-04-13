package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PcouponholdingDTO;
import com.ezticket.web.product.dto.PcouponholdingStatusDTO;
import com.ezticket.web.product.pojo.Pcouponholding;
import com.ezticket.web.product.pojo.PcouponholdingPK;
import com.ezticket.web.product.repository.PcouponholdingRepository;
import jakarta.transaction.Transactional;
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

    public List<PcouponholdingDTO> getPcouponHoldingByMemberno(Integer memberno){
        return pcouponholdingRepository.findByMemberno(memberno)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public PcouponholdingDTO getPcouponHoldingByID(PcouponholdingPK pcouponholdingPK) {
        Pcouponholding pcouponholding = pcouponholdingRepository.getReferenceById(pcouponholdingPK);
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
    @Transactional
    public boolean changePcouponHoldingStatus(PcouponholdingStatusDTO ps){
        PcouponholdingPK pcouponholdingPK = new PcouponholdingPK(ps.getPcouponno(),ps.getMemberno());
        Pcouponholding pcouponholding = pcouponholdingRepository.getReferenceById(pcouponholdingPK);
        pcouponholding.setPcouponstatus(ps.getPcouponstatus());
        pcouponholdingRepository.save(pcouponholding);
        return true;
    }

}
