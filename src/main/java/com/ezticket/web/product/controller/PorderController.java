package webapp.porder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webapp.porder.dto.PorderDTO;
import webapp.porder.dto.PorderDetailsDTO;
import webapp.porder.dto.PorderStatusDTO;
import webapp.porder.service.PorderService;

import java.util.List;

@RestController
@RequestMapping("/html")
public class PorderController {

    @Autowired private PorderService porderService;

    @GetMapping("/porderlist")
    public List<PorderDTO> getAllPorderlist(){
        return porderService.getAllPorder();
    }

    @GetMapping("/getporderbyid")
    public PorderDTO getDeliveryByID(@RequestParam Integer id){
        return porderService.getPorderByID(id);
    }
    @GetMapping("/GetPorderDetailsByID")
    public PorderDetailsDTO getDetailByID(@RequestParam Integer id){
        return porderService.getPorderDetailsByID(id);
    }
    @GetMapping("/getordersbyid")
    public List<PorderDTO> getPordersByID(@RequestParam Integer id){
        return porderService.getPordersByID(id);
    }
    @PostMapping("/updatestatusbyid")
    @ResponseBody
    public boolean updateStatusByID(@RequestBody PorderStatusDTO ps){
        porderService.updateByID(ps.getPorderno(), ps.getPprocessstatus());
        return true;
    }
}
