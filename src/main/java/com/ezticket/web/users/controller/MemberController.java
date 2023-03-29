package com.ezticket.web.users.controller;

import com.ezticket.web.users.dto.MemberDTO;
import com.ezticket.web.users.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/my_organisation_dashboard_member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/ga")
    public List<MemberDTO> getAllMember(){ return memberService.getAllMember(); }
}
