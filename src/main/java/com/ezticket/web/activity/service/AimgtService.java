package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.AimgtDto;
import com.ezticket.web.activity.pojo.Aimgt;
import com.ezticket.web.activity.repository.AimgtRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Aimgt findById(Integer aimgNo) {
        return aimgtRepository.findById(aimgNo).orElse(null);
    }

    public List<Aimgt> findAllByActivityNo(Integer activityNo){

        return aimgtRepository.findAllByActivityNo(activityNo)
                .stream()
                .collect(Collectors.toList());
    }

    public Aimgt findByActivityNo(Integer activityNo) {

        return aimgtRepository.findByActivityNo(activityNo);
    }



    private AimgtDto entityToDTO(Aimgt aimgt){
        AimgtDto aimgtDto = modelMapper.map(aimgt, AimgtDto.class);
        return aimgtDto;
   }


    public List<Aimgt> saveAimgt(List<Aimgt> aimgts) {
    return aimgtRepository.saveAll(aimgts);
    }

    public Aimgt saveOne(Aimgt aimgt) {
        return aimgtRepository.save(aimgt);
    }

    public void updateAimgt(List<Aimgt> aimgts) {
        aimgts.forEach(aimgt -> {
            if (aimgt.getAimgNo() != null) {
                // Update existing image in the database
                Aimgt existingAimgt = aimgtRepository.findById(aimgt.getAimgNo())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid image number"));
                existingAimgt.setAimg(aimgt.getAimg());
                existingAimgt.setAimgMain(aimgt.getAimgMain());
                aimgtRepository.save(existingAimgt);
            } else {
                // Insert new image into the database
                aimgtRepository.save(aimgt);
            }
        });
    }

    public void deleteAimgt(Integer aimgNo) {
        aimgtRepository.deleteById(aimgNo);
    }


    public void save(Aimgt aimgt) {
        aimgtRepository.save(aimgt);
    }
}
