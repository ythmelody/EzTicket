package com.ezticket.web.users.controller;

import com.ezticket.web.users.dto.RoleDTO;
import com.ezticket.web.users.pojo.Role;
import com.ezticket.web.users.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/back-users-my-team")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @GetMapping("/gr")
    public List<Role> getAllRole(){ return roleService.getAllRole(); };
    @GetMapping("/garf")
    public List<RoleDTO> getAllRolesWithFuncs() { return  roleService.getAllRolesWithFuncs(); }

}
