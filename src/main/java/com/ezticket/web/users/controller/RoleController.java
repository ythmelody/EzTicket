package com.ezticket.web.users.controller;

import com.ezticket.web.users.dto.RoleDTO;
import com.ezticket.web.users.pojo.Role;
import com.ezticket.web.users.repository.RoleRepository;
import com.ezticket.web.users.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;



    @GetMapping("/gr")
    public List<Role> getAllRole(){ return roleService.getAllRole(); };

    @GetMapping("/garf")
    public List<RoleDTO> getAllRolesWithFuncs() { return  roleService.getAllRolesWithFuncs(); }

    @PostMapping("/addNewRole")
    public Role createRole(@RequestBody Role newrole){
        System.out.println("insertThisRole: "+ newrole.toString());
        return roleRepository.save(newrole);
    }

    @PostMapping("/{roleno}/roleauth")
    public ResponseEntity<Role> toggleRoleStatus(@PathVariable("roleno") Integer roleno){
        Role updateRoleStatus = roleService.updateRoleStatus(roleno);
        System.out.println("Update Status roleno: "+roleno);
        return  ResponseEntity.ok(updateRoleStatus);
    }

}
