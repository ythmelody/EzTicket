package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.BlockPriceDto;
import com.ezticket.web.activity.pojo.BlockPrice;
import com.ezticket.web.activity.repository.BlockPriceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlockPriceService {
    @Autowired
    private BlockPriceRepository blockPriceRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<BlockPriceDto> findAll(){
        return blockPriceRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    private BlockPriceDto entityToDTO(BlockPrice blockPrice){
        return modelMapper.map(blockPrice,BlockPriceDto.class);
    }


}
