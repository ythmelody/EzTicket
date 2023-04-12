package com.ezticket.web.users.controller;

import com.ezticket.web.users.pojo.Host;
import com.ezticket.web.users.repository.HostRepository;
import com.ezticket.web.users.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/back-users-host")
public class HostController {
    @Autowired
    private HostService hostService;
    @Autowired
    private HostRepository hostRepository;
    @GetMapping("/gh")
    public List<Host> getAllHost(){ return  hostService.getAllHost(); }

    @PostMapping("/{hostno}")
    public ResponseEntity<Host> updateHost(@PathVariable("hostno")Integer hostno, @RequestBody Host newhost){
        Host updateHost = hostService.updateHost(hostno,newhost);
        //測試編輯輸入的資料有沒有進來
        System.out.println("hostno: " + hostno);
        System.out.println("host: " + newhost.toString());
        return  ResponseEntity.ok(updateHost);
    }

    @PostMapping("/insertHost")
    public Host createHost(@RequestBody Host newhost) {
        System.out.println("insertThisHost: " + newhost.toString());
        return hostRepository.save(newhost);
    }
}
