package com.ezticket.web.users.controller;

import com.ezticket.web.users.dto.MemberDTO;
import com.ezticket.web.users.dto.MemberImgDTO;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import com.ezticket.web.users.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    @GetMapping("/ga")
    public List<MemberDTO> getAllMember() {
        return memberService.getAllMember();
    }

    //更新編輯會員資料
    @PostMapping("/{memberno}")
    public ResponseEntity<Member> updateMember(@PathVariable("memberno") Integer memberno, @RequestBody MemberDTO newMemberDTO) {
        Member updateMember = memberService.updateMember(memberno, newMemberDTO);
        System.out.println("Edit Memberno: " + memberno);
        System.out.println(newMemberDTO.toString());
        return ResponseEntity.ok(updateMember);
    }

    //轉換會員權限的按鈕
    @PostMapping("/{memberno}/auth")
    public ResponseEntity<Member> toggleMemberStatus(@PathVariable("memberno") Integer memberno) {
        Member updateMemberStatus = memberService.updateMemberStatus(memberno);
        System.out.println("Update Status Memberno: " + memberno);
        return ResponseEntity.ok(updateMemberStatus);
    }

    //會員登入時的驗證並存到Session
    @PostMapping("/login")
    public Member login(HttpServletRequest request, @RequestBody Member member) {
        HttpSession session = request.getSession();
        if (memberService.authenticate(member.getMemail(), member.getMpassword())) {
            if (request.getSession(false) != null) {
                request.changeSessionId();
            }
            session.setAttribute("loggedin", true);
            session.setAttribute("member", member);
            member.setSuccessful(true);
        } else {
            member.setSuccessful(false);
            member.setMessage("查無此帳號或密碼");
        }
        return member;
    }

    //會員登入後,其他網頁從Session取得會員資料
    @GetMapping("/getMemberInfo")
    @ResponseBody
    public Member getMemberInfo(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member != null) {
            member = memberService.getMemberInfo(member.getMemail());
            member.setSuccessful(true);
            member.setMpassword("Secret");
        } else {
            member.setSuccessful(false);
            member.setMessage("會員未通過認證");
        }
        return member;
    }


    //會員上傳更新自己的大頭貼
    @PostMapping("/upload-mimg")
    public ResponseEntity<String> uploadMimg(@ModelAttribute MemberImgDTO memberImgDTO, @RequestParam("file") MultipartFile file, @RequestParam("memberno") Integer memberno) {
        try {
            System.out.println("上傳成功");
            memberImgDTO.setFile(file);
            memberImgDTO.setMemberno(memberno);
            memberService.updateMemImg(memberImgDTO);
            return ResponseEntity.ok("上傳成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //會員於後端改密碼
    @PostMapping("/changePwd")
    @ResponseBody
    public String changePassword(@RequestParam("oldpassword") String oldPassword,
                                 @RequestParam("newpassword") String newPassword,
                                 @RequestParam("confirmpassword") String confirmPassword,
                                 HttpSession session) {
        System.out.println("收到請求:"+ oldPassword);
        System.out.println(newPassword);
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            System.out.println("Session找不到登入的會員資料");
            return "error";
        }

        String status = memberService.updateMpassword(member.getMemail(),oldPassword,newPassword,confirmPassword);
        System.out.println("確認密碼完成存入新密碼!");
        return status;
    }

    //會員的備用收件人資料更新
    @PostMapping("/saveCpt")
    @ResponseBody
    public String saveBackupUser(@RequestBody Map<String, String> data, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            System.out.println("Session找不到登入的會員資料");
            return "error";
        }
        String comrecipient = data.get("comrecipient");
        String comrephone = data.get("comrephone");
        String comreaddress = data.get("comreaddress");
        // 儲存資料到資料庫
        String status = memberService.updateComrecipient(member.getMemail(), comrecipient, comrephone, comreaddress);
        System.out.println("備用收件人資料更新!");
        System.out.println(status);

        return status;
    }

    @PostMapping("/saveMemberEdit")
    @ResponseBody
    public String updateMember(@RequestBody Map<String, String> data, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            System.out.println("Session找不到登入的會員資料");
            return "error";
        }
        // 儲存資料到資料庫
        String status = memberService.updateMember(data);
        System.out.println("會員資料更新!");
        System.out.println(status);

        return status;
    }


}
