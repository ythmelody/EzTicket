package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.Seats;
import com.ezticket.web.activity.repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SeatsRepository seatsRepository;

    public List<Seats> getSeatsBySessionAndBlockNo(Integer sessionNo, Integer blockNo){
        return seatsRepository.findBySessionNoAndBlockNo(sessionNo,blockNo);
    }

    public int updateOneSeat(String blockName, String realX, String realY, int seatStatus, int seatNo){
        if(!(seatStatus == 1)){
            redisTemplate.opsForSet().remove("Session1", String.valueOf(seatNo));
        }

        if(seatStatus == 1){
            redisTemplate.opsForSet().add("Session1", String.valueOf(seatNo));
        }

        return seatsRepository.update(blockName, realX, realY, seatStatus, seatNo);
    }

    public boolean insertNewSeat(Seats seats){
        seatsRepository.save(seats);
        return true;
    }

    public int updateOneSeatStatus(int seatStatus, int seatNo){
        return seatsRepository.updateStatus(seatStatus, seatNo);
    }

//    public Set getToSellSeatsBySession(int sessionNo){
//        return seatsRepository.getToSellSeatsBySession(sessionNo);
//    }

}
