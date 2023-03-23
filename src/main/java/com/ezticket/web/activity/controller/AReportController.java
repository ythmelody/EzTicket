package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.AReportDto;
import com.ezticket.web.activity.service.AReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
    @RequestMapping("/areport")
    public class AReportController {

        @Autowired
        private AReportService aReportService;

        @GetMapping("/ListAll")
        public List<AReportDto> getAllReports() {
            return aReportService.getAllReports();
        }

        @GetMapping("/OneReport")
        public Optional<AReportDto> getOneReport(@RequestParam Integer reportId){
            return aReportService.getOneReport(reportId);
        }

}
