package com.ezticket.web.users.service;

import com.ezticket.web.users.dto.MemberDTO;
import com.ezticket.web.users.dto.MemberImgDTO;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<MemberDTO> getAllMember() {
        return memberRepository.findAll()
                .stream()
                .map(member -> MemberDTO.builder()
                        .memberno(member.getMemberno())
                        .mname(member.getMname())
                        .memail(member.getMemail())
                        .birth(member.getBirth())
                        .gender(member.getGender())
                        .mcell(member.getMcell())
                        .address(member.getAddress())
                        .memberstatus(member.getMemberstatus())
                        .build())
                .collect(Collectors.toList());
    }

    public Member updateMember(Integer memberno,MemberDTO newMemberDTO){
        Optional<Member> oldMember = memberRepository.findById(memberno);
        if(oldMember.isPresent()){
            Member updateTheMember = oldMember.get();
            updateTheMember.setMname(newMemberDTO.getMname());
            updateTheMember.setMemail(newMemberDTO.getMemail());
            updateTheMember.setBirth(newMemberDTO.getBirth());
            updateTheMember.setGender(newMemberDTO.getGender());
            updateTheMember.setMcell(newMemberDTO.getMcell());
            updateTheMember.setAddress(newMemberDTO.getAddress());
            return  memberRepository.save(updateTheMember);
        }else {
            throw new RuntimeException("Member not found with memberno: " + memberno);
        }
    }

    public Member updateMemberStatus(Integer memberno){
        Optional<Member> oldMember = memberRepository.findById(memberno);
        if(oldMember.isPresent()){
            Member updateTheMember = oldMember.get();
            if (updateTheMember.getMemberstatus() == 1) {
                updateTheMember.setMemberstatus(2);
            } else if (updateTheMember.getMemberstatus() == 2) {
                updateTheMember.setMemberstatus(1);
            }
            return memberRepository.save(updateTheMember);

        }else {
            throw new RuntimeException("Member not found with memberno: " + memberno);
        }
    }

    public boolean authenticate(String memail, String mpassword) {
        Member member = memberRepository.findByMemail(memail);
        if (member != null && member.getMpassword().equals(mpassword)) {
            return true;
        } else {
            return false;
        }

    }
    public Member getMemberInfo(String memail){
        Member member = memberRepository.findByMemail(memail);
        return member;
    }


    public Member updateMemImg(MemberImgDTO memberImgDTO) throws IOException {
        Optional<Member> member = memberRepository.findById(memberImgDTO.getMemberno());
        if(member.isPresent()){
            Member updateTheMember = member.get();
            updateTheMember.setMimg(memberImgDTO.getFile().getBytes());
            return  memberRepository.save(updateTheMember);
        }else {
            throw new RuntimeException("Member not found with memberno: " + memberImgDTO.getMemberno());
        }
    }

    public String updateMpassword(String email, String oldPassword, String newPassword, String confirmPassword){
        Optional<Member> member = Optional.ofNullable(memberRepository.findByMemail(email));
        if(member.isPresent()){
            Member updateTheMember = member.get();

            if (!updateTheMember.getMpassword().equals(oldPassword)) {
                System.out.println("輸入的舊密碼與真正的舊密碼不同!");
                return "oPwdError";
            }
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("新密碼與確認密碼不同!");
                return "newPwdError";
            }
            updateTheMember.setMpassword(newPassword);
            memberRepository.save(updateTheMember);
            return "success";
        }else {
            throw new RuntimeException("Member not found with Memail: " + email);
        }
    }

    public String updateComrecipient(String email, String comrecipient, String comrephone, String comreaddress){
        Optional<Member> member = Optional.ofNullable(memberRepository.findByMemail(email));
        if(member.isPresent()){
            Member updateTheMember = member.get();

            updateTheMember.setComrecipient(comrecipient);
            updateTheMember.setComrephone(comrephone);
            updateTheMember.setComreaddress(comreaddress);
            memberRepository.save(updateTheMember);
            return "success";
        }else {
            throw new RuntimeException("Member not found with Memail: " + email);
        }
    }

    public String updateMember(Map<String, String> data){
        Optional<Member> member = Optional.ofNullable(memberRepository.findByMemail(data.get("memail")));
        if(member.isPresent()){
            Member updateTheMember = member.get();

            updateTheMember.setMname(data.get("mname"));
            updateTheMember.setGender(Integer.valueOf(data.get("gender")));
            updateTheMember.setBirth(Date.valueOf(data.get("birth")));
            updateTheMember.setMcell("mcell");
            updateTheMember.setMphone("mphone");
            updateTheMember.setAddress("address");
            memberRepository.save(updateTheMember);
            return "success";
        }else {
            throw new RuntimeException("Member not found with Memail: " + data.get("memail"));
        }
    }


}
