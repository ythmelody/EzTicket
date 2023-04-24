package com.ezticket.web.users.controller;

import com.ezticket.core.service.EmailServiceImpl;
import com.ezticket.core.service.RedisService;
import com.ezticket.core.service.VerificationCodeService;
import com.ezticket.web.users.dto.MemSignUpDTO;
import com.ezticket.web.users.dto.MemberDTO;
import com.ezticket.web.users.dto.MemberImgDTO;
import com.ezticket.web.users.pojo.Backuser;
import com.ezticket.web.users.pojo.Member;
import com.ezticket.web.users.repository.MemberRepository;
import com.ezticket.web.users.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
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

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private RedisService redisService;

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
            session.setAttribute("loggedin", false);
            member.setSuccessful(false);
            member.setMessage("查無此帳號或密碼");
            System.out.println("查無此帳號或密碼");
        }
        return member;
    }

    //會員登入後,其他網頁從Session取得會員資料
    @GetMapping("/getMemberInfo")
    @ResponseBody
    public Member getMemberInfo(HttpSession session, HttpServletResponse response) throws IOException {
        Member loginMember = (Member) session.getAttribute("member");
        Boolean status = (Boolean) session.getAttribute("loggedin");
        System.out.println(status);
        Member member = new Member();
        if (status != null && status.booleanValue()) {
            // 已登入
            member = memberService.getMemberInfo(loginMember.getMemail());
            member.setMpassword("I don't know.");
            member.setSuccessful(true);
            System.out.println("會員已登入: " + member.getMname());
        } else {
            member.setSuccessful(false);
            System.out.println("會員未登入");
            // 未登入
        }
        return member;
    }


    //會員上傳更新自己的大頭貼
    @PostMapping("/upload-mimg")
    public ResponseEntity<String> uploadMimg(@ModelAttribute MemberImgDTO memberImgDTO, @RequestParam("file") MultipartFile file, @RequestParam("memberno") Integer memberno) {
        try {
            System.out.println("會員大頭貼上傳成功");
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
        System.out.println("收到改密碼請求:" + "(舊)" + oldPassword + " (新)" + newPassword);
        Map<String, String> resultMap = new HashMap<>();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            System.out.println("會員未登入!");
            resultMap.put("noSessionError", "會員未登入!");
            return resultMap;
        }
        //進Service驗證
        resultMap = memberService.updateMpassword(member.getMemail(), oldPassword, newPassword, confirmPassword);
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
            resultMap.put("noSessionError", "會員未登入!");
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
    public ResponseEntity<?> updateMember(@Valid @RequestBody MemberDTO memberDTO, BindingResult bindingResult, HttpSession session) {
        Map<String, String> errors = new HashMap<>();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            errors.put("noSessionError", "會員未登入!");
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


    //登出按鈕,讓session失效
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("會員登出成功!");
    }

    //忘記密碼頁面  第一步:確認有無此會員,有的話就寄驗證碼到信箱
    @RequestMapping("/checkemail/{email}")
    public boolean checkEmail(@PathVariable String email) throws MessagingException {
        Member member = memberService.getMemberInfo(email);
        if (member == null) {
            System.out.println("查無此會員!");
            return false;
        }

        System.out.println("會員驗證碼已送出!");
        String code = verificationCodeService.generateCode(email);
        emailServiceImpl.sendVerificationCode(email, code);
        return true;


    }

    //確認驗證碼是否正確
    @RequestMapping("/verify")
    public boolean verificationCode(@RequestParam String email, @RequestParam String code) {
        System.out.println(code);
        System.out.println(email);
        if (redisService.checkCode(email, code)) {
            System.out.println("驗證碼正確!");
            return true;
        }
        System.out.println("驗證碼錯誤!");
        return false;
    }

    //前台已驗證過直接儲存進去新密碼(忘記密碼)
    @PostMapping("/resetPwd")
    public void savePassword(@RequestParam("email") String email, @RequestParam("password") String password) {
        System.out.println("資料已進來");
        System.out.println(email);
        System.out.println(password);
        Member member =memberService.updateMemberPwd(email,password);
    }


    //忘記密碼頁面  第一步:確認有無此會員,有的話就寄驗證碼到信箱
    @RequestMapping("/sendValidCode/{email}")
    public void sendValidCode(@PathVariable String email) throws MessagingException {
        System.out.println("會員驗證碼已送出!");
        String code = verificationCodeService.generateCode(email);
        emailServiceImpl.sendVerificationCode(email, code);
    }


    //驗證資料和驗證碼是否正確,並新增會員
    @PostMapping("/insertMem")
    public ResponseEntity<?> signUpMember(@Valid @RequestBody MemSignUpDTO memSignUpDTO, BindingResult bindingResult){
        System.out.println("使用者註冊會員的提交資料:" + memSignUpDTO.toString());

        //先確認驗證碼是否正確
        boolean checkCode = redisService.checkCode(memSignUpDTO.getMemail(), memSignUpDTO.getValidcode());

        //確認有無此會員
        Member member = memberService.getMemberInfo(memSignUpDTO.getMemail());

        //確認密碼與密碼是否相符合
        String password = memSignUpDTO.getMpassword();
        String chPassword = memSignUpDTO.getChpassword();

        //驗證碼有錯 & 資料庫有此email 的話也加入錯誤訊息
        if (bindingResult.hasErrors() || !checkCode || member != null || !password.equals(chPassword)) {
            System.out.println("新增的資料格式有誤需調整");
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            if (!checkCode){
                errors.put("validcode","驗證碼錯誤,請確認或重新發送!");
                System.out.println("驗證碼錯誤!");
            }
            if(member != null){
                errors.put("memail","此Email已註冊,請至登入頁面!");
                System.out.println("此Email已註冊過!");
            }
            if(!password.equals(chPassword)){
                errors.put("chpassword","確認密碼與密碼不符,請再次確認!");
                System.out.println("確認密碼與密碼不符,請再次確認!");
            }

            return ResponseEntity.badRequest().body(errors);
        } else {
            System.out.println("新增成功");
            memberService.insertMember(memSignUpDTO);
            return  ResponseEntity.ok("success");
        }
    }



}