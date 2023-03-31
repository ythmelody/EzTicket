package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.AReportDto;
import com.ezticket.web.activity.pojo.AReport;
import com.ezticket.web.activity.repository.AReportDao;
import com.ezticket.web.activity.repository.AReportRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Transactional
public class AReportService {
    @Autowired
    private AReportRepository aReportRepository;
    @Autowired
    private AReportDao aReportDao;
    @Autowired
    private ModelMapper modelMapper;

    //    取得所有檢舉評論
    public List<AReportDto> getAllAReports(){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return aReportRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    //    取得一筆檢舉評論
    public Optional<AReport> getAReportForUpdate(Integer reportId){
        return aReportRepository.findById(reportId);
    }

    public int updateAReport(int reportId, int reportStatus){
        return aReportRepository.update(reportId, reportStatus);
    }

    public List<AReportDto> getAReportsByActName(String actName){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return aReportRepository.getAReportByActName(actName)
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    public List getAReportAnames(){
        return  aReportRepository.getAReportANames();
    }

    public List<AReport> getAReportsBySelection(Map map){
        return  aReportDao.getByCompositeQuery(map);
    }

    public boolean inserAReport(AReport aReport){
        aReport.setAReportDate(new Date(System.currentTimeMillis()));
        aReport.setAReportStatus(0);
        aReportRepository.save(aReport);
        return true;
    }

    private AReportDto EntityToDTO(AReport aReport) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        AReportDto aReportDto = new AReportDto();
        aReportDto = modelMapper.map(aReport, AReportDto.class);
        return aReportDto;
    }


}
