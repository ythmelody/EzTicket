package com.ezticket.web.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private Integer memberno;
    @NotBlank(message = "名字不可為空")
    private String mname;
    @NotBlank(message = "信箱不可為空")
    @Email(message = "請輸入正確的電子信箱格式")
    private String memail;
    private Date birth;
    private Integer gender;
    @NotBlank(message = "手機不可為空")
    @Pattern(regexp = "^09\\d{8}$", message = "手機必須為09開頭的10位數字")
    private String mcell;
    @NotBlank(message = "地址不可為空")
    private String address;
//    @Pattern(regexp = "^(0)([0-9]{1})([-]?)([0-9]{6,8})$", message = "室內電話格式錯誤")
    private String mphone;
    private Integer memberstatus;

}
