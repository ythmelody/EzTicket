package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PdetailsDTO;
import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.repository.PdetailsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        PdetailsDTO pdetailsDTO = new PdetailsDTO();
        pdetailsDTO = modelMapper.map(pdetails, PdetailsDTO.class);

        return pdetailsDTO;
    }
    public List<PdetailsDTO> getAllPdetails(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        return pdetailsRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

}
