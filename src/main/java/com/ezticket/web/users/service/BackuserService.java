package com.ezticket.web.users.service;

import com.ezticket.web.users.dto.BackuserDTO;
import com.ezticket.web.users.dto.RoleDTO;
import com.ezticket.web.users.pojo.*;
import com.ezticket.web.users.repository.BackuserRepository;
import com.ezticket.web.users.repository.FunctionRepository;
import com.ezticket.web.users.repository.RoleRepository;
import com.ezticket.web.users.repository.RoleauthorityRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BackuserService {

    @Autowired
    private BackuserRepository backuserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleauthorityRepository roleauthorityRepository;

    @Autowired
    private FunctionRepository functionRepository;

    //拿取所有後台成員的資料,過濾只要DTO的內容
    public List<BackuserDTO> getAllBackuser() {
        return backuserRepository.findAll()
                .stream()
                .map(backuser -> BackuserDTO.builder()
                        .bano(backuser.getBano())
                        .baaccount(backuser.getBaaccount())
                        .baname(backuser.getBaname())
                        .baemail(backuser.getBaemail())
                        .baroleno(backuser.getBaroleno())
                        .bacell(backuser.getBacell())
                        .bastatus(backuser.getBastatus())
                        .build())
                .collect(Collectors.toList());
    }

    //更新後台成員資料,只針對DTO的欄位即可
    public Backuser updateBackuser(Integer backuserno,BackuserDTO newBackuserDTO){
        Optional<Backuser> oldBackuser = backuserRepository.findById(backuserno);
        if(oldBackuser.isPresent()){
            Backuser updateTheBackuser = oldBackuser.get();
            updateTheBackuser.setBaname(newBackuserDTO.getBaname());
            updateTheBackuser.setBaemail(newBackuserDTO.getBaemail());
            updateTheBackuser.setBaroleno(newBackuserDTO.getBaroleno());
            updateTheBackuser.setBacell(newBackuserDTO.getBacell());
            return  backuserRepository.save(updateTheBackuser);
        }else {
            throw new RuntimeException("Backuser not found with backuserno: " + backuserno);
        }
    }

    //toggle後台成員的權限狀態  1 <-> 0
    public Backuser updateBackuserStatus(Integer bano){
        Optional<Backuser> oldBackuser = backuserRepository.findById(bano);
        if(oldBackuser.isPresent()){
            Backuser updateTheBa = oldBackuser.get();
            if (updateTheBa.getBastatus() == 1) {
                updateTheBa.setBastatus(0);
            } else if (updateTheBa.getBastatus() == 0) {
                updateTheBa.setBastatus(1);
            }
            return backuserRepository.save(updateTheBa);

        }else {
            throw new RuntimeException("Backuser not found with bano: " + bano);
        }
    }

    //驗證資料庫有無此後台成員,回傳布林值
    public boolean authenticate(String baaccount, String bapassword) {
        Backuser backuser = backuserRepository.findByBaaccount(baaccount);
        if (backuser != null && backuser.getBapassword().equals(bapassword) && backuser.getBastatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    //取得單一後台成員的所有基本資料 //未寫完
    public Backuser getBackuserInfo(String baaccount){
        Backuser backuser = backuserRepository.findByBaaccount(baaccount);
        return backuser;
    }

    //透過後台成員的baroleno,來取得單一後台成員的角色/擁有功能/沒有的功能
    public RoleDTO getBaRolesWithFuncs(String baaccount){
        RoleDTO roleDTO = new RoleDTO();
        Backuser backuser = backuserRepository.findByBaaccount(baaccount);
        Optional<Role> role = roleRepository.findById(backuser.getBaroleno());
        roleDTO.setRoleno(backuser.getBaroleno());
        roleDTO.setRolename(role.get().getRolename());
        roleDTO.setRolestatus(role.get().getRolestatus());
        //根據後台使用者的角色,存取此角色擁有的功能進DTO
        Map<Integer,String> funcMap = new HashMap<>();
        //把角色的所有功能{funcno:funcname}放進List
        List<Roleauthority> roleauthorities = roleauthorityRepository.findByRoleno(role.get().getRoleno());
        for (Roleauthority roleauthority : roleauthorities){
            //透過角色的所有功能的funcno來找到funcno和funcname並放入funcMap
            Function allfuncs = functionRepository.findByFuncno(roleauthority.getFuncno());
            funcMap.put(allfuncs.getFuncno(),allfuncs.getFuncname());
        }
        //存入roleDTO
        roleDTO.setFuncs(funcMap);


        //存取此角色缺少的功能進DTO
        //取該角色"擁有的功能"的所有key(funcno)放進Set
        Set<Integer> roleFuncKey = funcMap.keySet();

        //取得功能表格裡的"所有功能"的funcno放進第二個Set
        Set<Integer> allFuncKey = new HashSet<>();
        List<Function> allFuncs = functionRepository.findAll();
        for(Function funcs : allFuncs){
            allFuncKey.add(funcs.getFuncno());
        }

        //把所有功能的funcno 去移除掉  該角色擁有的funcno = 該角色沒有的funcno
        Set<Integer> missFuncs = new HashSet<>(allFuncKey);
        missFuncs.removeAll(roleFuncKey);

        //透過該角色沒有的funcno 找到funcname,並存入nofuncMap裡
        Map<Integer, String> nofuncMap = new HashMap<>();
        for(Integer funcno : missFuncs){
            nofuncMap.put(funcno, functionRepository.findByFuncno(funcno).getFuncname());
        }
        //存入roleDTO
        roleDTO.setNofuncs(nofuncMap);

        return roleDTO;
    }
}
