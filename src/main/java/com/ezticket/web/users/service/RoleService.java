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

//    public List<RoleDTO> getAllRolesWithFuncs() {
//        List<Role> roles = roleRepository.findAll();
//        return roles.stream().map(role -> {
//            RoleDTO roleDTO = new RoleDTO();
//            roleDTO.setRoleno(role.getRoleno());
//            roleDTO.setRolename(role.getRolename());
//            roleDTO.setRolestatus(role.getRolestatus());
//            Map<Integer, String> funcMap = new HashMap<>();
//            List<Roleauthority> roleAuthorities = roleauthorityRepository.findByRoleno(role.getRoleno());
//            for (Roleauthority roleAuthority : roleAuthorities) {
//                Function allFuncs = functionRepository.findByFuncno(roleAuthority.getFuncno());
//                funcMap.put(allFuncs.getFuncno(), allFuncs.getFuncname());
//            }
//            roleDTO.setFuncs(funcMap);
//
//            // Find the missing functions
//            Set<Integer> roleFuncKey = funcMap.keySet();
//            Set<Integer> allFuncKey = new HashSet<>();
//            List<Function> allFuncs = functionRepository.findAll();
//            for (Function func : allFuncs) {
//                allFuncKey.add(func.getFuncno());
//            }
//            Set<Integer> missFuncs = new HashSet<>(allFuncKey);
//            missFuncs.removeAll(roleFuncKey);
//
//            // Add the missing functions to the nofuncMap
//            Map<Integer, String> nofuncMap = new HashMap<>();
//            for (Integer funcno : missFuncs) {
//                String funcName = allFuncs.stream()
//                        .filter(func -> func.getFuncno().equals(funcno))
//                        .map(Function::getFuncname)
//                        .findFirst()
//                        .orElse(null);
//                nofuncMap.put(funcno, funcName);
//            }
//            roleDTO.setNofuncs(nofuncMap);
//
//            return roleDTO;
//        }).collect(Collectors.toList());
//    }
}