package com.ezticket.web.users.service;

import com.ezticket.web.users.dto.BackuserDTO;
import com.ezticket.web.users.repository.BackuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
