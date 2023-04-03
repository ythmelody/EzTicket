package com.ezticket.web.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {
    private Integer roleno;

    private String rolename;

    private Integer rolestatus;

    private Map<Integer, String> funcs;

    private Map<Integer,String> nofuncs;
}
