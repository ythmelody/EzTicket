package webapp.pdetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapp.pdetails.dto.PdetailsDTO;
import webapp.pdetails.service.PdetailsService;

import java.util.List;

@RestController
@RequestMapping("/html")
public class PdetailsController {

    @Autowired private PdetailsService pdetailsService;

    @GetMapping("/pdetailslist")
    public List<PdetailsDTO> getAllPdetailslist(){return pdetailsService.getAllPdetails();}

}
