package com.ezticket.web.users.dto;

import jakarta.persistence.Column;
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
    private String mname;
    private String memail;
    private Date birth;
    private Integer gender;
    private String mcell;
    private String address;
    private Integer memberstatus;

}
