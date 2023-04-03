package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.AReportDto;
import com.ezticket.web.activity.pojo.AReport;
import com.ezticket.web.activity.service.AReportService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public Optional<AReport> getAReportForUpdate(@RequestParam Integer reportId) {
        return aReportService.getAReportForUpdate(reportId);
    }

    @GetMapping("/UpdateOneAReport")
    public int updateAReport(@RequestParam int reportId, @RequestParam int reportStatus) {
        return aReportService.updateAReport(reportId, reportStatus);
    }

    @GetMapping("/ByActName")
    public List<AReportDto> getAReportsByActName(String actName) {
        return aReportService.getAReportsByActName(actName);
    }

    ;

    @GetMapping("/AReportANames")
    public List getAReportANames() {
        return aReportService.getAReportAnames();
    }

    @PostMapping("/BySelection")
    public List<AReport> getAReportsBySelection(@RequestBody String jsonData) {
//            System.out.println(jsonData);  {"activityno":"all", "areportstatus":"all"}
        Gson gson = new Gson();
        Map map = gson.fromJson(jsonData, Map.class);
        System.out.println(map); // {activityno=all, areportstatus=1}
        return aReportService.getAReportsBySelection(map);
    }

    @PostMapping("/insert")
    public boolean insertAReport(@RequestBody String jsonData) {
        Gson gson = new Gson();
        AReport aReport = gson.fromJson(jsonData, AReport.class);
        return aReportService.inserAReport(aReport);
    }
}
