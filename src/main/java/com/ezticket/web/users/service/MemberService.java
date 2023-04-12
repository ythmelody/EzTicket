package com.ezticket.web.users.service;

import com.ezticket.web.users.dto.BackuserDTO;
import com.ezticket.web.users.dto.MemberDTO;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
