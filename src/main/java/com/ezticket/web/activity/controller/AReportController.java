package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.AReportDto;
import com.ezticket.web.activity.pojo.AReport;
import com.ezticket.web.activity.service.AReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/areport")
public class AReportController {

        @Autowired
        private AReportService aReportService;

        @GetMapping("/ListAll")
        public List<AReportDto> getAllAReports() {
            return aReportService.getAllAReports();
        }

        @GetMapping("/ListOneAReport")
        public Optional<AReport> getAReportForUpdate(@RequestParam Integer reportId){
            return aReportService.getAReportForUpdate(reportId);
        }

        @GetMapping("/UpdateOneAReport")
        public int updateAReport(@RequestParam int reportId, @RequestParam int reportStatus){
            return aReportService.updateAReport(reportId, reportStatus);
        }

        @GetMapping("/ByActName")
        public List<AReportDto> getAReportsByActName(String actName) {
            return aReportService.getAReportsByActName(actName);
        };



}
