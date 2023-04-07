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

import java.util.*;
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
                Function allFuncs = functionRepository.findByFuncno(roleAuthority.getFuncno());
                funcMap.put(allFuncs.getFuncno(), allFuncs.getFuncname());
            }
            roleDTO.setFuncs(funcMap);

//          role缺少的功能
            Set<Integer> roleFuncKey = funcMap.keySet();  //取角色功能的所有key
            Set<Integer> allFuncKey = new HashSet<>();
            List<Function> allFuncs = functionRepository.findAll();
            for(Function funcs : allFuncs){
                allFuncKey.add(funcs.getFuncno());
            }
            Set<Integer> missFuncs = new HashSet<>(allFuncKey);
            missFuncs.removeAll(roleFuncKey);
            Map<Integer, String> nofuncMap = new HashMap<>();
            for(Integer funcno : missFuncs){
                nofuncMap.put(funcno, functionRepository.findByFuncno(funcno).getFuncname());
            }
            roleDTO.setNofuncs(nofuncMap);
            return roleDTO;
        }).collect(Collectors.toList());
    }

    public  Role updateRoleStatus(Integer roleno){
        Optional<Role> oldRole =roleRepository.findById(roleno);
        if (oldRole.isPresent()){
            Role updateTheRole = oldRole.get();
            if(updateTheRole.getRolestatus() == 1){
                updateTheRole.setRolestatus(0);
            } else if (updateTheRole.getRolestatus() == 0) {
                updateTheRole.setRolestatus(1);
            }
            return roleRepository.save(updateTheRole);
        }else {
            throw new RuntimeException("Role not found with roleno: " + roleno);
        }
    }
}