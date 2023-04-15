package com.ezticket.web.users.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HOST")
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOSTNO")
    private Integer hostno;
    @NotBlank(message = "廠商名字不可為空")
    @Column(name = "HOSTNAME")
    private String hostname;
    @NotBlank(message = "聯絡人不可為空")
    @Column(name = "HOSTCONTACT")
    private String hostcontact;
    @NotBlank(message = "信箱不可為空")
    @Email(message = "請輸入正確的電子信箱格式")
    @Column(name = "HOSTEMAIL")
    private String hostemail;
    @NotBlank(message = "手機不可為空")
    @Pattern(regexp = "^09\\d{8}$", message = "手機必須為09開頭的10位數字")
    @Column(name = "HOSTCELL")
    private String hostcell;
}
