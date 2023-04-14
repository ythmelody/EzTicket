package com.ezticket.web.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BackuserDTO {
    private Integer bano;
    private String baaccount;
    @NotBlank(message = "名字不可為空")
    private String baname;
    @NotBlank(message = "信箱不可為空")
    @Email(message = "請輸入正確的電子信箱格式")
    private String baemail;
    private Integer baroleno;
    @NotBlank(message = "手機不可為空")
    @Pattern(regexp = "^09\\d{8}$", message = "手機必須為09開頭的10位數字")
    private String bacell;
    private Integer bastatus;
}
