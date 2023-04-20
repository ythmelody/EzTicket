package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.PlaceModelDTO;
import com.ezticket.web.activity.pojo.PlaceModel;
import com.ezticket.web.activity.repository.PlaceModelRepository;
import com.ezticket.web.activity.service.PlaceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/PlaceModel")
public class PlaceModelController {
    @Autowired
    private PlaceModelService placeModelService;

    @GetMapping("/GetAll")
    public List<PlaceModelDTO> getAll() {
        return placeModelService.getAllDTO();
    }

    @GetMapping("/GetActive")
    public List<PlaceModelDTO> getActive() {
        return placeModelService.getActive();
    }

    @GetMapping("/GetDisabled")
    public List<PlaceModelDTO> getDisabled() {
        return placeModelService.getDisabled();
    }

    @GetMapping("/Get/{modelno}")
    public PlaceModelDTO getOne(@PathVariable("modelno") Integer modelno) {
        return placeModelService.findByIdDTO(modelno);
    }

//    保留資料驗證的空間。錯誤訊息用 ResponseEntity.badRequest().body(errors) 回傳
    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<?> insertModel(@RequestBody PlaceModelDTO placeModelDTO){
        PlaceModelDTO placeModelDTOr = placeModelService.savePlaceModel(placeModelDTO);
        return ResponseEntity.ok(placeModelDTOr);
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> editModel(@RequestBody PlaceModelDTO placeModelDTO){
        PlaceModelDTO placeModelDTOr = placeModelService.savePlaceModel(placeModelDTO);
        return ResponseEntity.ok(placeModelDTOr);
    }

    @PostMapping("/delete/{modelno}")
    public void deleteModel(@PathVariable("modelno") Integer modelno){
        placeModelService.deleteModel(modelno);
        System.out.println("PlaceModel " + modelno + " is deleted.");
    }

//  想取看看所有關聯的資料
    @Autowired
    PlaceModelRepository placeModelRepository;
    @GetMapping("/GetRelatedInfo/{modelno}")
    public PlaceModel bigOne(@PathVariable("modelno") Integer modelno){
        return placeModelService.findById(modelno).orElse(new PlaceModel());
    }

}
