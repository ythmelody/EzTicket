package com.ezticket.web.users.service;

import com.ezticket.web.users.dto.BackuserDTO;
import com.ezticket.web.users.pojo.Backuser;
import com.ezticket.web.users.repository.BackuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BackuserService {

    @Autowired
    private BackuserRepository backuserRepository;


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

}
