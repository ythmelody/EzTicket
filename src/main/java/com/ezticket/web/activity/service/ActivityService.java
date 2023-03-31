package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.ActivityDto;
import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.repository.ActivityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    private ActivityDto entityToDTO(Activity activity){
        return modelMapper.map(activity,ActivityDto.class);
    }
}
