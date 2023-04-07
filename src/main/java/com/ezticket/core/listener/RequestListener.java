package com.ezticket.core.listener;

import com.ezticket.web.activity.repository.SeatsRepository;
import com.ezticket.web.activity.repository.SessionRepository;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

//@Component
public class RequestListener implements ServletRequestListener, ServletRequestAttributeListener {
//
//    @Autowired
//    private SessionRepository sessionRepository;
//
//    @Autowired
//    private SeatsRepository seatsRepository;
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;


//    測試過也不是你
//    @Override
//    public void requestInitialized(ServletRequestEvent sre) {
//        System.out.println("requestInitialized 被觸發了!!");
//        List<Integer> sessionNos = sessionRepository.getSessionNos();
//
//        for(int sessionNo: sessionNos){
//            SetOperations<String, String> set = redisTemplate.opsForSet();
//
//            Set<Integer> toSellSeats = seatsRepository.getToSellSeatsBySession(sessionNo);
//            for(int toSellSeat: toSellSeats){
//                set.add(("Session" + sessionNo), String.valueOf(toSellSeat));
//            }
//        }
//    }


//    不是你
//    @Override
//    public void attributeAdded(ServletRequestAttributeEvent srae) {
//        System.out.println("attributeAdded 被觸發了!!");
//        List<Integer> sessionNos = sessionRepository.getSessionNos();
//
//        for(int sessionNo: sessionNos){
//            SetOperations<String, String> set = redisTemplate.opsForSet();
//
//            Set<Integer> toSellSeats = seatsRepository.getToSellSeatsBySession(sessionNo);
//            for(int toSellSeat: toSellSeats){
//                set.add(("Session" + sessionNo), String.valueOf(toSellSeat));
//            }
//        }
//    }


//    這個可以
//    @Override
//    public void attributeRemoved(ServletRequestAttributeEvent srae) {
//        System.out.println("attributeRemoved 被觸發了!!");
//        List<Integer> sessionNos = sessionRepository.getSessionNos();
//
//        for(int sessionNo: sessionNos){
//            SetOperations<String, String> set = redisTemplate.opsForSet();
//
//            Set<Integer> toSellSeats = seatsRepository.getToSellSeatsBySession(sessionNo);
//            for(int toSellSeat: toSellSeats){
//                set.add(("Session" + sessionNo), String.valueOf(toSellSeat));
//            }
//        }
//    }

//    不是你
//    @Override
//    public void attributeReplaced(ServletRequestAttributeEvent srae) {
//        System.out.println("attributeReplaced 被觸發了!!");
//        List<Integer> sessionNos = sessionRepository.getSessionNos();
//
//        for(int sessionNo: sessionNos){
//            SetOperations<String, String> set = redisTemplate.opsForSet();
//
//            Set<Integer> toSellSeats = seatsRepository.getToSellSeatsBySession(sessionNo);
//            for(int toSellSeat: toSellSeats){
//                set.add(("Session" + sessionNo), String.valueOf(toSellSeat));
//            }
//        }
//    }

//    不是你
//    @Override
//    public void requestDestroyed(ServletRequestEvent sre) {
//        System.out.println("requestDestroyed 被觸發了!!");
//        List<Integer> sessionNos = sessionRepository.getSessionNos();
//
//        for(int sessionNo: sessionNos){
//            SetOperations<String, String> set = redisTemplate.opsForSet();
//
//            Set<Integer> toSellSeats = seatsRepository.getToSellSeatsBySession(sessionNo);
//            for(int toSellSeat: toSellSeats){
//                set.add(("Session" + sessionNo), String.valueOf(toSellSeat));
//            }
//        }
//    }

}
