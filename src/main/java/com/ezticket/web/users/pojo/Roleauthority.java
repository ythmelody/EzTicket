package com.ezticket.web.users.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLEAUTHORITY")
@IdClass(RoleauthorityPK.class)
public class Roleauthority {
    @Id
    @Column(name = "ROLENO")
    private Integer roleno;
    @Id
    @Column(name = "FUNCNO")
    private Integer funcno;


}
