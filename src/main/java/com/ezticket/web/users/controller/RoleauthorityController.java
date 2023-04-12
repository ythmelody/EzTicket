package com.ezticket.web.users.controller;

import com.ezticket.web.users.pojo.Roleauthority;
import com.ezticket.web.users.repository.RoleauthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roleauthority")
public class RoleauthorityController {

    @Autowired
    private RoleauthorityRepository roleauthorityRepository;
    @PostMapping("/addRoleauthority")
    public Roleauthority addAuth(@RequestBody Roleauthority newRoleauthority){
        System.out.println("addNewAuth (roleno And funcno): " + newRoleauthority.toString());
        return roleauthorityRepository.save(newRoleauthority);

    }

    @PostMapping("/removeRoleauthority")
    public Roleauthority removeAuth(@RequestBody Roleauthority roleauthority){
        System.out.println("removeAuth (roleno And funcno): " + roleauthority.toString());
        roleauthorityRepository.delete(roleauthority);
        return roleauthority;
    }


}
