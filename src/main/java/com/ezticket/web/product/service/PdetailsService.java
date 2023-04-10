package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PdetailsDTO;
import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.repository.PdetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class PdetailsService {
    @Autowired
    private PdetailsRepository pdetailsRepository;

    @Autowired
    private ModelMapper modelMapper;


    public PdetailsDTO EntityToDTO(Pdetails pdetails){
        return modelMapper.map(pdetails, PdetailsDTO.class);
    }
    public List<PdetailsDTO> getAllPdetails(){
        return pdetailsRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public List<PdetailsDTO> getPdetailsByPorderno(Integer porderno){
        return pdetailsRepository.findByPorderno(porderno)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

}
