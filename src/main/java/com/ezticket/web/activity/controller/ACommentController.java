package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.ACommentDto;

import com.ezticket.web.activity.pojo.AComment;
import com.ezticket.web.activity.pojo.AReport;
import com.ezticket.web.activity.service.ACommentService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/acomment")
public class ACommentController {

    @Autowired
    private ACommentService aCommentService;

    @GetMapping("/ListAll")
    public List<ACommentDto> getAllAComments() {
        return aCommentService.getAllAComments();
    }

    @GetMapping("/ListOneAComment")
    public Optional<AComment> getAReportForUpdate(@RequestParam Integer commentId){
        return aCommentService.getACommentForUpdate(commentId);
    }

    @GetMapping("/UpdateOneAComment")
    public int updateAReport(@RequestParam int commentId, @RequestParam int commentStatus){
        return aCommentService.updateAComment(commentId, commentStatus);
    }

    @GetMapping("/ByActName") public List<ACommentDto> getAReportsByActName(String actName) {
        return aCommentService.getACommentsByActName(actName);
    };

    @GetMapping("/ACommentANames")
    public List getACommentANames(){
        return aCommentService.getACommentAnames();
    }

    @PostMapping("/BySelection")
    public List<AComment> getACommentsBySelection(@RequestBody String jsonData) {
        Gson gson = new Gson();
        Map map = gson.fromJson(jsonData, Map.class);
        System.out.println(map); // {activityno=all, areportstatus=1}
        return aCommentService.getACommentsBySelection(map);
    };

    @GetMapping("/ActAComments")
    public List<ACommentDto> getACommentByActNo(@RequestParam Integer actNo){ return aCommentService.getACommentByActNo(actNo); }
    @PostMapping("/insert")
    public Boolean insertAComment(@RequestBody String jsonData){
        Gson gson = new Gson();
        AComment aComment = gson.fromJson(jsonData, AComment.class);
        return aCommentService.insertAComment(aComment);
    }

}
