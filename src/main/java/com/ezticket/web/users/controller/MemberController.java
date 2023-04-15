package com.ezticket.web.users.controller;

import com.ezticket.web.users.dto.MemberDTO;
import com.ezticket.web.users.dto.MemberImgDTO;
import com.ezticket.web.users.pojo.Backuser;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import com.ezticket.web.users.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    //拿到所有會員的資料
    @GetMapping("/ga")
    public List<MemberDTO> getAllMember() {
        return memberService.getAllMember();
    }

    //更新編輯會員資料
    //要驗證的VO或DTO加上驗證的annotation,並且在參數前面加上@Valid,跟加上BindingResult參數用來裝錯誤message
    @PostMapping("/{memberno}")
    public ResponseEntity<?> updateMember(@PathVariable("memberno") Integer memberno, @Valid @RequestBody MemberDTO newMemberDTO, BindingResult bindingResult) {
        //測試前端輸入的資料有無進來
        System.out.println("要求編輯的會員資料Memberno: " + memberno);
        System.out.println(newMemberDTO.toString());

        //如果驗證的DTO或VO有問題bindingResult就可以利用方法get到欄位名以及欄位驗證錯誤的message
        //如果欄位驗證確定有錯誤bindingResult就會有值
        //這時可以用Map把bindingResult->get到欄位名當key,get到的錯誤message當Value
        //把裝滿錯誤資訊的Map丟回去前端去做處理,利用key(欄位名)把錯誤訊息放進相應的input標籤後面
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        } else {
            //如果bindingResult取不到值代表沒錯誤,就進Service執行操作資料庫
            Member updateMember = memberService.updateMember(memberno, newMemberDTO);
            return ResponseEntity.ok(updateMember);
        }
    }

    //轉換會員權限的按鈕
    @PostMapping("/{memberno}/auth")
    public ResponseEntity<Member> toggleMemberStatus(@PathVariable("memberno") Integer memberno) {
        Member updateMemberStatus = memberService.updateMemberStatus(memberno);
        System.out.println("Update Status Memberno: " + memberno);
        return ResponseEntity.ok(updateMemberStatus);
    }

    //會員登入時的驗證且存到Session
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
            System.out.println("登入驗證成功");
        } else {
            member.setSuccessful(false);
            member.setMessage("查無此帳號或密碼");
            System.out.println("查無此帳號或密碼");
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
            member.setMpassword("第二組everybody年薪百萬!");
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
    public Map<String, String> changePassword(@RequestParam("oldpassword") String oldPassword,
                                 @RequestParam("newpassword") String newPassword,
                                 @RequestParam("confirmpassword") String confirmPassword,
                                 HttpSession session) {
        System.out.println("收到改密碼請求:"+ "(舊)"+ oldPassword +" (新)"+newPassword);
        Map<String, String> resultMap = new HashMap<>();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            System.out.println("會員未登入!");
            resultMap.put("noSessionError","會員未登入!");
            return resultMap;
        }
        //進Service驗證
        resultMap = memberService.updateMpassword(member.getMemail(),oldPassword,newPassword,confirmPassword);
        return resultMap;
    }

    //會員的備用收件人資料更新
    @PostMapping("/saveCpt")
    @ResponseBody
    public Map<String, String> saveBackupUser(@RequestBody Map<String, String> data, HttpSession session) {
        Map<String, String> resultMap = new HashMap<>();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            System.out.println("會員未登入!");
            resultMap.put("noSessionError","會員未登入!");
            return resultMap;
        }
        String comrecipient = data.get("comrecipient");
        String comrephone = data.get("comrephone");
        String comreaddress = data.get("comreaddress");
        System.out.println(comrecipient);
        // 進Service驗證
        resultMap = memberService.updateComrecipient(member.getMemail(), comrecipient, comrephone, comreaddress);
        System.out.println("備用收件人驗證完成返回驗證結果!");
        return resultMap;

    }

    //會員自己編輯資料更新儲存
    @PostMapping("/saveMemberEdit")
    @ResponseBody
    public ResponseEntity<?> updateMember(@Valid @RequestBody MemberDTO memberDTO,BindingResult bindingResult, HttpSession session) {
        Map<String, String> errors = new HashMap<>();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            errors.put("noSessionError","會員未登入!");
            System.out.println("Session找不到登入的會員資料");
            return ResponseEntity.badRequest().body(errors);
        }
        if (bindingResult.hasErrors()) {
            System.out.println("資料格式異常");
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        } else {
            // 儲存資料到資料庫
            System.out.println("資料儲存完成,會員資料更新!");
            Member updateMember = memberService.updateMember(memberDTO);
            return ResponseEntity.ok(updateMember);
        }
    }


}
