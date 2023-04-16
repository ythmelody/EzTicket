package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.service.PlaceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/PlaceModel")
public class PlaceModelController {
    @Autowired
    private PlaceModelService placeModelService;


}
