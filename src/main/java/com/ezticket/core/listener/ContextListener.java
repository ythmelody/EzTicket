package com.ezticket.core.listener;

import com.ezticket.web.activity.repository.SeatsRepository;
import com.ezticket.web.activity.repository.SessionRepository;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ContextListener implements ServletContextListener {

//    @Autowired
//    private SessionRepository sessionRepository;
//    @Autowired
//    private SeatsRepository seatsRepository;
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        04/10: Redis 改為儲存鎖定座位
//        Set<Integer> sessions = sessionRepository.getToSellSessions();
//        for(int session: sessions){
//            SetOperations<String, String> set = redisTemplate.opsForSet();
//            Set<Integer> toSellSeats = seatsRepository.getToSellSeatsBySession(session);
//            for(int toSellSeat: toSellSeats){
//                set.add(("Session" + session), String.valueOf(toSellSeat));
//            }
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

//        04/10: Redis 改為儲存鎖定座位
//        Set<Integer> sessions = sessionRepository.getToSellSessions();
//        for(int session: sessions){
//            redisTemplate.delete("Session" + session);
//        }
    }

}
