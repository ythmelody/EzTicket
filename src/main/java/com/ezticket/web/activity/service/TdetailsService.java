package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.TdetailsDto;
import com.ezticket.web.activity.pojo.Tdetails;
import com.ezticket.web.activity.repository.TdetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TdetailsService {
    @Autowired
    private TdetailsRepository adetailsRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<TdetailsDto> findAll(){
        return adetailsRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

    }

    private TdetailsDto entityToDTO(Tdetails adetails){
        return modelMapper.map(adetails,TdetailsDto.class);
    }
}
