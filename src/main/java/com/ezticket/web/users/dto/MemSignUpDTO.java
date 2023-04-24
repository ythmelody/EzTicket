package com.ezticket.web.users.dto;

import jakarta.persistence.Column;
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
public class MemSignUpDTO {

    @NotBlank(message = "名字不可為空")
    private String mname;

    @NotBlank(message = "信箱不可為空")
    @Email(message = "請輸入正確的電子信箱格式")
    private String memail;

    @NotBlank(message = "手機不可為空")
    @Pattern(regexp = "^09\\d{8}$", message = "手機必須為09開頭的10位數字")
    private String mcell;
    private Date birth;

    @NotBlank(message = "地址不可為空")
    private String address;
    @NotBlank(message = "密碼不得為空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,12}$", message = "密碼格式錯誤!請輸入8-12字元,並至少一個大小寫字母和數字!")
    @Column(name = "MPASSWORD")
    private String mpassword;

    @Column(name = "CHPASSWORD")
    private String chpassword;

    @Column(name = "VALIDCODE")
    private String validcode;
}
