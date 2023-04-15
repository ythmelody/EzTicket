package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.AimgtDto;
import com.ezticket.web.activity.dto.SessionDto;
import com.ezticket.web.activity.dto.TorderDto;
import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.pojo.Seats;
import com.ezticket.web.activity.pojo.Session;
import com.ezticket.web.activity.service.SessionService;
import com.google.gson.Gson;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/Session")
public class SessionController {
    @Autowired
    private SessionService sessionService;
    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    @GetMapping("/findAll")
    public List<SessionDto> findAll() {

        return sessionService.findAll();
    }

    @GetMapping("/findByactivityNo")
    public List<SessionDto> findByactivityNo(@RequestParam Integer activityNo) {

        return sessionService.findByactivityNo(activityNo);
    }

    //    Add by Shawn on 4/3
    @GetMapping("/ActSessions")
    public List<SessionDto> getAllSessionByActNo(@RequestParam Integer actNo) {
        return sessionService.getAllSessionByActNo(actNo);
    }

    //    Add by Shawn on 04/08
    @GetMapping("/updateTicketQTY")
    public Integer updateTicketQTY(@RequestParam Integer ticketChange, @RequestParam Integer sessionNo) {
        return sessionService.updateTicketQTY(ticketChange, sessionNo);
    }

    //    Add by Shawn on 04/12
    @GetMapping("/findById")
    public Optional<SessionDto> findById(@RequestParam Integer sessionNo) {

        return sessionService.findById(sessionNo);
    }
    @PostMapping("/saveSession")
    public boolean saveSession(@RequestBody Session session) throws ParseException {
        sessionService.saveSession(session);
        return true;
    }
//    @PostMapping("/saveSession")
//    public Session saveSession(@RequestParam("sessionsTime") String sessionsTime,
//                               @RequestParam("sessioneTime") String sessioneTime,
//                               @RequestParam("maxSeatsQty") Integer maxSeatsQty,
//                               @RequestParam("maxStandingQty") Integer maxStandingQty,
//                               @RequestParam("activityNo") Integer activityNo) throws ParseException {
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//        Date sessionsTime1 = format.parse(sessionsTime);
//        Date sessioneTime1 = format.parse(sessioneTime);
//        Session session = new Session();
//        session.setSessionsTime(sessionsTime1);
//        session.setSessioneTime(sessioneTime1);
//        session.setMaxSeatsQty(maxSeatsQty);
//        session.setMaxStandingQty(maxStandingQty);
//        session.setActivityNo(activityNo);
//        session.setSeatsQty(0);
//        session.setStandingQty(0);
//
//        return sessionService.saveSession(session);
//    }
}


