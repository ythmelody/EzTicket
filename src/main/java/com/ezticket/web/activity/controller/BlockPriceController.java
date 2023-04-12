package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.BlockPriceDto;
import com.ezticket.web.activity.pojo.BlockPrice;
import com.ezticket.web.activity.service.BlockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/BlockPrice")
public class BlockPriceController {
    @Autowired
    private BlockPriceService blockPriceService;
    @GetMapping("/findAll")
    public List<BlockPriceDto> findAll(){
        return blockPriceService.findAll();
    }

//    Add by Shawn on 04/08
    @GetMapping("/findById")
    public BlockPrice findBlockById(@RequestParam Integer blockNo){
        return blockPriceService.findBlockById(blockNo);
    }

//    Add by Shawn on 04/11
    @GetMapping("/findByActNo")
    public List<BlockPrice> getBlockPriceByActivityNo(@RequestParam int activityNo){
        return blockPriceService.getBlockPriceByActivityNo(activityNo);
    }
}
