package com.ezticket.web.users.service;

import com.ezticket.web.users.pojo.Roleauthority;
import com.ezticket.web.users.repository.RoleauthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleauthorityService {

    @Autowired
    private RoleauthorityRepository roleauthorityRepository;


    //得到該角色的所有funcno
    public List<Integer> getAllfuncno(Integer roleno) {
        List<Roleauthority> roleAuthorities = roleauthorityRepository.findByRoleno(roleno);
        List<Integer> funcnos = new ArrayList<>();

        for (Roleauthority roleAuthority : roleAuthorities) {
            funcnos.add(roleAuthority.getFuncno());
        }
        return funcnos;
    }

}
