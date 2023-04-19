package com.ezticket.web.product.service;

import com.ezticket.core.pojo.EmailDetails;
import com.ezticket.core.service.EmailService;
import com.ezticket.web.product.dto.PcouponholdingDTO;
import com.ezticket.web.product.dto.PcouponholdingStatusDTO;
import com.ezticket.web.product.pojo.Pcoupon;
import com.ezticket.web.product.pojo.Pcouponholding;
import com.ezticket.web.product.pojo.PcouponholdingPK;
import com.ezticket.web.product.repository.PcouponRepository;
import com.ezticket.web.product.repository.PcouponholdingRepository;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
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
    @Autowired
    private EmailService emailService;

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
    public boolean takePcoupon(Integer memberno, Integer pcouponno) {
        PcouponholdingPK pcouponholdingPK = new PcouponholdingPK();
        pcouponholdingPK.setMemberno(memberno);
        pcouponholdingPK.setPcouponno(pcouponno);
        if (pcouponholdingRepository.existsById(pcouponholdingPK)){
            return false;
        }
        Pcouponholding pcouponholding = pcouponholdingRepository.findById(pcouponholdingPK)
                .orElseGet(() -> {
                    Pcouponholding newPcouponholding = new Pcouponholding();
                    newPcouponholding.setPcouponholdingPK(pcouponholdingPK);
                    newPcouponholding.setPcouponstatus((byte) 0);
                    return pcouponholdingRepository.save(newPcouponholding);
                });
        return true;
    }

    @Transactional
    public boolean takePcouponAllMember(Integer pcouponno) {
        List<Member> Members = memberRepository.findAll();
        for (Member member : Members) {
            PcouponholdingPK pcouponholdingPK = new PcouponholdingPK();
            pcouponholdingPK.setMemberno(member.getMemberno());
            pcouponholdingPK.setPcouponno(pcouponno);

            Pcouponholding pcouponholding = pcouponholdingRepository.findById(pcouponholdingPK)
                    .orElseGet(() -> {
                        Pcouponholding newPcouponholding = new Pcouponholding();
                        newPcouponholding.setPcouponholdingPK(pcouponholdingPK);
                        newPcouponholding.setPcouponstatus((byte) 0);
                        return pcouponholdingRepository.save(newPcouponholding);
                    });
            Pcoupon pcoupon = pcouponRepository.getReferenceById(pcouponno);
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient("ezticketsystem@gmail.com");
            emailDetails.setSubject("ezTicket: 恭喜您獲得一張" + pcoupon.getPcouponname() + "優惠券");
            emailDetails.setMsgBody(
                    "此為系統通知信件:\n" +
                            "親愛的會員，\n" +
                            "\n" +
                            "感謝您一直以來對我們網站的支持，我們在此送上一張優惠券，讓您享受更多的優惠！\n" +
                            "\n" +
                            "優惠券資訊如下：\n" +
                            "\n" +
                            "優惠券編號：[" + pcoupon.getPcouponno() + "]\n" +
                            "\n" +
                            "優惠券名稱：["+ pcoupon.getPcouponname() + "]\n" +
                            "\n" +
                            "優惠金額：["+ pcoupon.getPdiscount() + "]\n" +
                            "\n" +
                            "使用期限：["+ pcoupon.getPcoupnedate() + "]\n" +
                            "\n" +
                            "使用條件：["+ pcoupon.getPreachprice() + "]\n" +
                            "\n" +
                            "如需使用此優惠券，請在結帳頁面輸入優惠券編號即可享受優惠。\n" +
                            "\n" +
                            "注意事項：\n" +
                            "優惠券不得與其他優惠同時使用。\n" +
                            "優惠券僅限在有效期內使用，逾期作廢。\n" +
                            "如有任何疑問，請隨時聯繫我們的客服人員。\n" +
                            "再次感謝您對我們網站的支持，祝您購物愉快！\n" +
                            "此致\n" +
                            "敬禮\n" +
                            "\n" +
                            "\n" +
                            "                                   [ezTicket - 一站式購票體驗]");
            emailService.sendSimpleMail(emailDetails);
            System.out.println(member.getMname() + "發送成功");
        }
        return true;
    }

}
