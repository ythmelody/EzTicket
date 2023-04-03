package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.TorderDto;
import com.ezticket.web.activity.pojo.Torder;
import com.ezticket.web.activity.repository.TorderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TorderService {
    @Autowired
    private TorderRepository torderRepository;
    @Autowired
    private ModelMapper modelMapper;


    public List<TorderDto> findAll(){
        return torderRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    public Optional<TorderDto> findById(Integer torderNo){
        return torderRepository.findById(torderNo).map(this::entityToDTO);
    }

    private TorderDto entityToDTO(Torder torder){
        TorderDto torderDto = new TorderDto();
        torderDto = modelMapper.map(torder,TorderDto.class);
        return torderDto;
    }
}
