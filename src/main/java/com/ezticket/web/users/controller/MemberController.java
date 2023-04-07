package com.ezticket.web.users.controller;

import com.ezticket.web.users.dto.MemberDTO;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/back-users-member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @GetMapping("/ga")
    public List<MemberDTO> getAllMember(){ return memberService.getAllMember(); }

    @PostMapping("/{memberno}")
    public ResponseEntity<Member> updateMember(@PathVariable("memberno")Integer memberno,@RequestBody MemberDTO newMemberDTO){
        Member updateMember = memberService.updateMember(memberno,newMemberDTO);
        System.out.println("Edit Memberno: " + memberno);
        System.out.println(newMemberDTO.toString());
        return ResponseEntity.ok(updateMember);
    }

    @PostMapping("/{memberno}/auth")
    public ResponseEntity<Member> toggleMemberStatus(@PathVariable("memberno") Integer memberno){
        Member updateMemberStatus = memberService.updateMemberStatus(memberno);
        System.out.println("Update Status Memberno: " + memberno);
        return ResponseEntity.ok(updateMemberStatus);
    }

}
