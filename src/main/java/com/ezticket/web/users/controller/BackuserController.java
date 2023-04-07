package com.ezticket.web.users.controller;

import com.ezticket.web.users.dto.BackuserDTO;
import com.ezticket.web.users.pojo.Backuser;
import com.ezticket.web.users.repository.BackuserRepository;
import com.ezticket.web.users.service.BackuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/back-users-my-team")
public class BackuserController {
    @Autowired
    private BackuserService backuserService;
    @Autowired
    private BackuserRepository backuserRepository;

    @GetMapping("/ga")
    public List<BackuserDTO> getAllBackuser(){ return backuserService.getAllBackuser(); }

    @PostMapping("/{backuserno}")
    public ResponseEntity<Backuser> updateBackuser(@PathVariable("backuserno")Integer backuserno,@RequestBody BackuserDTO newBackuserDTO){
        Backuser updateBackuser = backuserService.updateBackuser(backuserno,newBackuserDTO);
        System.out.println("Edit Backuserno: " + backuserno);
        System.out.println(newBackuserDTO.toString());
        return ResponseEntity.ok(updateBackuser);
    }

    @PostMapping("/{bano}/auth")
    public ResponseEntity<Backuser> toggleBackuserStatus(@PathVariable("bano") Integer bano){
        Backuser updateBackuserStatus = backuserService.updateBackuserStatus(bano);
        System.out.println("Update Status Bano: " + bano);
        return ResponseEntity.ok(updateBackuserStatus);
    }

    @PostMapping("/insertBu")
    public Backuser createBackuser(@RequestBody Backuser newbackuser){
        System.out.println("insertThisBackuser" + newbackuser.toString());
        return  backuserRepository.save(newbackuser);
    }
}
