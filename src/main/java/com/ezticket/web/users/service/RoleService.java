package com.ezticket.web.users.service;

import com.ezticket.web.users.dto.RoleDTO;
import com.ezticket.web.users.pojo.Function;
import com.ezticket.web.users.pojo.Role;
import com.ezticket.web.users.pojo.Roleauthority;
import com.ezticket.web.users.repository.FunctionRepository;
import com.ezticket.web.users.repository.RoleRepository;
import com.ezticket.web.users.repository.RoleauthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FunctionRepository functionRepository;

    @Autowired
    private RoleauthorityRepository roleauthorityRepository;


    public List<Role> getAllRole(){
        return roleRepository.findAll()
                .stream().toList();
    }
    public List<RoleDTO> getAllRolesWithFuncs() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRoleno(role.getRoleno());
            roleDTO.setRolename(role.getRolename());
            roleDTO.setRolestatus(role.getRolestatus());
            Map<Integer, String> funcMap = new HashMap<>();
            List<Roleauthority> roleAuthorities = roleauthorityRepository.findByRoleno(role.getRoleno());
            for (Roleauthority roleAuthority : roleAuthorities) {
                Function function = functionRepository.findByFuncno(roleAuthority.getFuncno());
                funcMap.put(function.getFuncno(), function.getFuncname());
            }
            roleDTO.setFuncs(funcMap);
            return roleDTO;
        }).collect(Collectors.toList());
    }
}