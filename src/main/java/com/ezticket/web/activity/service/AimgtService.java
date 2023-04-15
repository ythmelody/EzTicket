package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.AimgtDto;
import com.ezticket.web.activity.pojo.Aimgt;
import com.ezticket.web.activity.repository.AimgtRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AimgtService {
    @Autowired
    private AimgtRepository aimgtRepository;
    @Autowired
    private ModelMapper modelMapper;

public List<AimgtDto> findAll(){
    return aimgtRepository.findAll()
            .stream()
            .map(this::entityToDTO)
            .collect(Collectors.toList());


    }

    public Optional<AimgtDto> findById(Integer aimgNo) {
        return aimgtRepository.findById(aimgNo).map(this::entityToDTO);
    }

    public List<AimgtDto> findAllByActivityNo(Integer activityNo){

        return aimgtRepository.findAllByActivityNo(activityNo)
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }


    private AimgtDto entityToDTO(Aimgt aimgt){
        AimgtDto aimgtDto = modelMapper.map(aimgt, AimgtDto.class);
        return aimgtDto;
   }


    public List<Aimgt> saveAimgt(List<Aimgt> aimgts) {
    return aimgtRepository.saveAll(aimgts);
    }
}
