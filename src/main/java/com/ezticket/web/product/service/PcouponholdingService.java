package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PcouponholdingDTO;
import com.ezticket.web.product.dto.PcouponholdingStatusDTO;
import com.ezticket.web.product.pojo.Pcoupon;
import com.ezticket.web.product.pojo.Pcouponholding;
import com.ezticket.web.product.pojo.PcouponholdingPK;
import com.ezticket.web.product.repository.PcouponRepository;
import com.ezticket.web.product.repository.PcouponholdingRepository;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private MemberRepository memberRepository;
    @Autowired
    private PcouponRepository pcouponRepository;
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
        pcouponholding.getPcoupon().getPcouponholdings().forEach(holding -> holding.getPcoupon().setPcouponholdings(null));
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
        PcouponholdingPK pcouponholdingPK = new PcouponholdingPK(ps.getPcouponno() ,ps.getMemberno());
        Pcouponholding pcouponholding = pcouponholdingRepository.getReferenceById(pcouponholdingPK);
        pcouponholding.setPcouponstatus(ps.getPcouponstatus());
        pcouponholdingRepository.save(pcouponholding);
        return true;
    }
    @Transactional
    public boolean takePcoupon(Integer memberno,Integer pcouponno){
        PcouponholdingPK pcouponholdingPK = new PcouponholdingPK();
        pcouponholdingPK.setMemberno(memberno);
        pcouponholdingPK.setPcouponno(pcouponno);
        Pcouponholding pcouponholding = new Pcouponholding();
        pcouponholding.setPcouponholdingPK(pcouponholdingPK);
        Pcouponholding oldholding = pcouponholdingRepository.getReferenceById(pcouponholding.getPcouponholdingPK());
        if (oldholding.getPcouponstatus() == 1 || oldholding.getPcouponstatus() == 0) {
            return false;
        } else {
            pcouponholding.setPcouponstatus((byte) 0);
            pcouponholdingRepository.save(pcouponholding);
            return true;
        }
    }
    @Transactional
    public boolean takePcouponAllMember(Integer pcouponno) throws EntityNotFoundException {
        List<Member> Members = memberRepository.findAll();
        for (Member member : Members) {
            PcouponholdingPK pcouponholdingPK = new PcouponholdingPK();
            pcouponholdingPK.setMemberno(member.getMemberno());
            pcouponholdingPK.setPcouponno(pcouponno);
            Pcouponholding pcouponholding = new Pcouponholding();
            pcouponholding.setPcouponholdingPK(pcouponholdingPK);
            try {
                Pcouponholding oldholding = pcouponholdingRepository.getReferenceById(pcouponholding.getPcouponholdingPK());
                if (oldholding.getPcouponstatus() == 1 || oldholding.getPcouponstatus() == 0) {
                    System.out.println("我有了");
                }
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
                pcouponholding.setPcouponstatus((byte) 0);
                pcouponholding.setPcoupon(pcouponRepository.findById(pcouponno).get());
                try {
                    pcouponholdingRepository.save(pcouponholding);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
        return true;
    }

}
