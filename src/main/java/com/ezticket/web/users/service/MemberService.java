package com.ezticket.web.users.service;

import com.ezticket.web.users.dto.BackuserDTO;
import com.ezticket.web.users.dto.MemberDTO;
import com.ezticket.web.users.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
