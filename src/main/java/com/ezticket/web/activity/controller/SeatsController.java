package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.pojo.Seats;
import com.ezticket.web.activity.service.SeatsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/seats")
public class SeatsController {

    @Autowired
    private SeatsService seatsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/BySessionAndBlock")
    public List<Seats> getSeatsBySessionAndBlockNo(@RequestParam Integer sessionNo, @RequestParam Integer blockNo) {
        return seatsService.getSeatsBySessionAndBlockNo(sessionNo, blockNo);
    }

    @GetMapping("/getBlockHasSeats")
    public List<Integer> getActBlockHasSeats(@RequestParam int actNo) {
        return seatsService.getActBlockHasSeats(actNo);
    }

    @GetMapping("/isSessionBlockSeatsExist")
    public boolean isSessionBlockSeatsExist(@RequestParam int sessionNo, @RequestParam int blockNo) {
        return seatsService.isSessionBlockSeatsExist(sessionNo, blockNo);
    }

    @GetMapping("/getLockedSeatsBySession")
    public Set<String> getLockedSeatsBySession(@RequestParam int sessionNo){
        return seatsService.getLockedSeatsBySession(sessionNo);
    }

    @PostMapping("/updateOneSeat")
    public int updateOneSeat(@RequestBody String jsonData) {
        Gson gson = new Gson();
        Seats seats = gson.fromJson(jsonData, Seats.class);
        seatsService.updateOneSeat(seats.getBlockName(), seats.getRealX(), seats.getRealY(), seats.getSeatStatus(), seats.getSeatNo());
        return 1;
    }

    @PostMapping("/insertNewSeat")
    public boolean insertNewSeats(@RequestBody String jsonData) {
        Gson gson = new Gson();
        Seats seat = gson.fromJson(jsonData, Seats.class);
        seatsService.insertNewSeat(seat);
        return true;
    }

    @GetMapping("/updateSeatStatus")
    public int updateSeatStatus(@RequestParam int seatStatus, @RequestParam int seatNo, @RequestParam int sessionNo) {
        return seatsService.setSessionSeats(seatStatus, seatNo, sessionNo);
    }

    @GetMapping("/getTicketsBystem")
    public List<Integer> getTicketsBySystem(@RequestParam int ticketQTY, @RequestParam int blockNo, @RequestParam int sessionNo) {
        return seatsService.getSeatsBySystem(ticketQTY,blockNo,sessionNo);
    }

    @GetMapping("/getSeatsBySession")
    public boolean getSeasBySession(@RequestParam int sessionNo){
        return seatsService.getSeasBySession(sessionNo);
    }

    @GetMapping("/getSeatsNumbers")
    public List<Integer> getSeatsNumbers (@RequestParam int sessionNo,@RequestParam  int blockNo){
        return seatsService.getSeatsNumbers(sessionNo, blockNo);
    }

    @GetMapping("/copySessionSeats")
    public boolean copySessionSeats (@RequestParam int copiedSessionNo, @RequestParam int sessionNo, @RequestParam int activityNo){ // HTML 對應 back-activity-seatmgt-existed.html
        return seatsService.copySessionSeats(copiedSessionNo, sessionNo, activityNo);
    }

    @GetMapping("/copyModelSeats")
    public boolean copyModelSeats (@RequestParam int modelNo, @RequestParam int sessionNo, @RequestParam int activityNo){ // HTML 對應 back-activity-seatmgt-existed.html
        return seatsService.copyModelSeats(modelNo, sessionNo, activityNo);
    }

    @GetMapping("/deleteSeats")
    public boolean deleteSeats(@RequestParam Integer sessionNo, @RequestParam Integer blockNo){
        return seatsService.deleteSeats(sessionNo, blockNo);
    }

}
