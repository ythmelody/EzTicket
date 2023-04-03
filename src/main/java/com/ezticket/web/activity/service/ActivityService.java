package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.ActivityDto;
import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.repository.ActivityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ActivityDto> findAll(){
        return activityRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    public Optional<ActivityDto> findByaName(String aName){
        return activityRepository.findByaName(aName).map(this::entityToDTO);

    }
    private ActivityDto entityToDTO(Activity activity){

        ActivityDto activityDto = modelMapper.map(activity,ActivityDto.class);
        activityDto.setActivityNo(activity.getActivityNo());
        return activityDto;
    }
}
