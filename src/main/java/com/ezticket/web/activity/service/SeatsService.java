package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.Seats;
import com.ezticket.web.activity.repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatsService {

    @Autowired
    private SeatsRepository seatsRepository;

    public List<Seats> getSeatsBySessionAndBlockNo(Integer sessionNo, Integer blockNo){
        return seatsRepository.findBySessionNoAndBlockNo(sessionNo,blockNo);
    }

    public int updateOneSeat(String blockName, String realX, String realY, int seatStatus, int seatNo){
        return seatsRepository.update(blockName, realX, realY, seatStatus, seatNo);
    }

    public boolean insertNewSeat(Seats seats){
        seatsRepository.save(seats);
        return true;
    }

}
