package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.AReportDto;
import com.ezticket.web.activity.pojo.AReport;
import com.ezticket.web.activity.repository.AReportRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AReportService {
    @Autowired
    private AReportRepository aReportRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<AReportDto> getAllReports(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return aReportRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AReportDto> getOneReport(Integer reportId){
        return aReportRepository.findById(reportId).map(this::EntityToDTO);
    }

    private AReportDto EntityToDTO(AReport aReport) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        AReportDto aReportDto = new AReportDto();
        aReportDto = modelMapper.map(aReport, AReportDto.class);
        return aReportDto;
    }

}
