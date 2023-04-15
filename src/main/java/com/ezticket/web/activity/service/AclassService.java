package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.AclassDto;
import com.ezticket.web.activity.pojo.Aclass;
import com.ezticket.web.activity.repository.AclassRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AclassService {
    @Autowired
    private AclassRepository aclassRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<AclassDto> findAll(){
    return aclassRepository.findAll()
            .stream()
            .map(this::entityToDTO)
            .collect(Collectors.toList());
}

private AclassDto entityToDTO (Aclass aclass){
    return modelMapper.map(aclass,AclassDto.class);
}

    public boolean saveAclass(Aclass jsonData) {
        aclassRepository.save(jsonData);
        return true;
    }
}
