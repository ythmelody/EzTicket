package com.ezticket.web.users.pojo;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleauthorityPK implements Serializable {
    private Integer roleno;

    private Integer funcno;
}
