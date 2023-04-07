package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.pojo.Seats;
import com.ezticket.web.activity.service.SeatsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatsController {

    @Autowired
    private SeatsService seatsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/BySessionAndBlock")
    public List<Seats> getSeatsBySessionAndBlockNo(@RequestParam Integer sessionNo, @RequestParam Integer blockNo){
        return seatsService.getSeatsBySessionAndBlockNo(sessionNo,blockNo);
    }

    @PostMapping("/updateOneSeat")
    public int updateOneSeat(@RequestBody String jsonData){
        Gson gson = new Gson();
        Seats seats = gson.fromJson(jsonData, Seats.class);
        seatsService.updateOneSeat(seats.getBlockName(), seats.getRealX(), seats.getRealY(), seats.getSeatStatus(), seats.getSeatNo());
        return 1;
    }

    @PostMapping("/insertNewSeat")
    public boolean insertNewSeats(@RequestBody String jsonData){
        Gson gson = new Gson();
        Seats seats = gson.fromJson(jsonData, Seats.class);
        seatsService.insertNewSeat(seats);
        return true;
    }

    @GetMapping("/updateSeatStatus")
    public int updateOneSeatStatus(@RequestParam int seatStatus,@RequestParam  int seatNo, @RequestParam int sessionNo){
        if(seatStatus == 3){
            redisTemplate.opsForSet().remove("Session" + sessionNo, String.valueOf(seatNo));
        } else {
            redisTemplate.opsForSet().add("Session" + sessionNo, String.valueOf(seatNo));
        }

        return seatsService.updateOneSeatStatus(seatStatus, seatNo);
    }

//    @GetMapping("/getToSellSeatsBySession")
//    public Set getSeatsBySession(@RequestParam int sessionNo){
//        return seatsService.getToSellSeatsBySession(sessionNo);
//    }

}
