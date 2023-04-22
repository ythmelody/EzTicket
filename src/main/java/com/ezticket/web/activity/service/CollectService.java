package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.CollectVO;
import com.ezticket.web.activity.pojo.TicketHolder;
import com.ezticket.web.activity.repository.CollectDAO;
import com.ezticket.web.activity.repository.TicketHolderDAO;
import com.ezticket.web.activity.repository.impl.CollectDAOImpl;
import com.ezticket.web.activity.repository.impl.TicketHolderDAOImpl;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import com.ezticket.web.users.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Service
public class CollectService {
    private TicketHolderDAO thdao;

//  Field Injection 抓不到MemberRepository實體
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private MemberService memberService;
//    @Autowired
//    public CollectService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//        thdao = new TicketHolderDAOImpl();
//    }

    public CollectService() {

        thdao = new TicketHolderDAOImpl();

    }
//    public Member findByMemail(String memail) {
//        return memberRepository.findByMemail(memail);
//    }

    //    欠會員的修改功能

//    public String updateCollect(Integer collectno, String memail) {
//
//        CollectDAO cdao = new CollectDAOImpl();
// Step1:  用 email 查出會員編號
//        取不到memberRepository......
//        Member member = memberRepository.findByMemail(memail);
//        Member member = findByMemail(memail);
//
//        Member member = memberService.getMemberInfo(memail);
//        if (member==null) {
//            return "查無此會員";
//        }
//        Integer memberno = member.getMemberno();

// Step2: 更新 collect 表格中的會員編號
//        CollectVO oldCollectVO = cdao.findByPK(collectno);
//        CollectVO collectVO = new CollectVO();
//        collectVO.setCollectno(collectno);
//        collectVO.setMemberno(memberno);
//        collectVO.settDetailsno(oldCollectVO.gettDetailsno());
//        collectVO.settStatus(oldCollectVO.gettStatus());
//        collectVO.setQrcode(oldCollectVO.getQrcode());
//        cdao.update(collectVO);
//        return "分票成功！";
//    }


    public TicketHolder getOneTicket(Integer collectno) {
        return thdao.findByCollectno(collectno);
    }

    public List<TicketHolder> getByMemberno(Integer memberno) {
        return thdao.getByMemberno(memberno);
    }
    public List<TicketHolder> getAll() {
        return thdao.getAll();
    }

    public String blobToBase64(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
