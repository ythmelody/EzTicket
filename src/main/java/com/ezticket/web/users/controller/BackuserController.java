package com.ezticket.web.users.controller;

import com.ezticket.web.users.dto.BackuserDTO;
import com.ezticket.web.users.pojo.Backuser;
import com.ezticket.web.users.service.BackuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/my_organisation_dashboard_my_team")
public class BackuserController {
    @Autowired
    private BackuserService backuserService;

    @GetMapping("/ga")
    public List<BackuserDTO> getAllBackuser(){ return backuserService.getAllBackuser(); }

}
