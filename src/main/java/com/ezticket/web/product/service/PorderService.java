package com.ezticket.web.product.service;

import com.ezticket.web.product.dto.PorderDTO;
import com.ezticket.web.product.dto.PorderDetailsDTO;
import com.ezticket.web.product.pojo.Porder;
import com.ezticket.web.product.repository.PorderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PorderService {

    @Autowired
    private PorderRepository porderRepository;
    @Autowired
    private ModelMapper modelMapper;

    // GetPordersByID
    public List<PorderDTO> getPordersByID(Integer id){
        return porderRepository.findByID(id)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }
    public PorderDTO getPorderByID(Integer id) {
        Porder porder = porderRepository.getReferenceById(id);
        return EntityToDTO(porder);
    }
    // GetPorderDetailsByID
    public PorderDetailsDTO getPorderDetailsByID(Integer id) {
        Porder porder = porderRepository.findByPorderno(id);
        return EntityToDetailDTO(porder);
    }
    public PorderDetailsDTO EntityToDetailDTO(Porder porder){
        PorderDetailsDTO porderDetailsDTO = modelMapper.map(porder, PorderDetailsDTO.class);
        porderDetailsDTO.setProducts(porder.getProducts());
        return porderDetailsDTO;
    }

    // UpdatePorderByID
    public PorderDTO updateByID(Integer id, Integer processStatus) {
        Porder porder = porderRepository.getReferenceById(id);
        porder.setPprocessstatus(processStatus);
        Porder updatedPorder = porderRepository.save(porder);
        return EntityToDTO(updatedPorder);
    }

    public PorderDTO EntityToDTO(Porder porder){
        return modelMapper.map(porder, PorderDTO.class);
    }
    public List<PorderDTO> getAllPorder(){
    return porderRepository.findAll()
            .stream()
            .map(this::EntityToDTO)
            .collect(Collectors.toList());
    }

}
