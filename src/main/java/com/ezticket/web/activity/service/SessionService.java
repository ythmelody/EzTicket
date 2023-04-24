package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.SessionDto;
import com.ezticket.web.activity.pojo.BlockPrice;
import com.ezticket.web.activity.pojo.Seats;
import com.ezticket.web.activity.pojo.Session;
import com.ezticket.web.activity.repository.SeatsRepository;
import com.ezticket.web.activity.repository.SessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private SeatsService seatsService;
    @Autowired
    private SeatsRepository seatsRepository;

    @Autowired
    private BlockPriceService blockPriceService;

    public List<SessionDto> findAll() {
        return sessionRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public List<SessionDto> findByactivityNo(Integer activityNo) {
        return sessionRepository.findByactivityNo(activityNo).stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    private SessionDto entityToDTO(Session session) {
        return modelMapper.map(session, SessionDto.class);
    }

    //    Add by Shawn on 4/3
    public List<SessionDto> getAllSessionByActNo(Integer actNo) {
        return sessionRepository.findByActivityNo(actNo)
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    //    Add by Shawn on 04/08
    public Integer updateTicketQTY(Integer ticketChange, Integer sessionNo) {
        return sessionRepository.updateStandingQtyById(ticketChange, sessionNo);
    }

    //    Add by Shawn on 04/11
    public Optional<SessionDto> findById(Integer sessionNo){
        return sessionRepository.findById(sessionNo).map(this::entityToDTO);
    }

    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    //    Add by Shawn on 04/19
    @Scheduled(cron = "0 30 * * * *")
    public void updateSessionInfo(){
        List<Integer> actNos = activityService.findActNosByWetherSeatIsTrue();

        for(Integer actNo: actNos){
            List<Session> sessions = sessionRepository.findByActivityNo(actNo);
            for(Session session: sessions){
                Map<String, Integer> info = seatsService.getSessionInfo(session.getSessionNo());
                session.setMaxSeatsQty(info.get("maxSeatsQty"));
                session.setMaxStandingQty(info.get("maxStandingQty"));
                session.setSeatsQty(info.get("seatsQty"));
                session.setStandingQty(info.get("standingQty"));
                sessionRepository.save(session);
            }
        }
    }



    public Session updateSession(Session session) {
        Session existingSession = sessionRepository.findById(session.getSessionNo()).orElse(null);
        if (existingSession == null) {
            return null;
        }
        existingSession.setSessionsTime(session.getSessionsTime());
        existingSession.setSessioneTime(session.getSessioneTime());
        existingSession.setMaxStandingQty(session.getMaxStandingQty());
        existingSession.setMaxSeatsQty(session.getMaxSeatsQty());

        // save the updated session entity to the database
        return sessionRepository.save(existingSession);
    }

    public void deleteSessionTwo(Integer sessionNo) {
        System.out.println("aaaaaaaaa");
        seatsRepository.deleteBySessionNo(sessionNo);
        System.out.println("bbbbbbbbb");
        sessionRepository.deleteById(sessionNo);
        System.out.println("ccccccccc");
    }


    //    Add by Shawn on 04/22
    // 當使用者進到選頁面時，將顯示每個區域的剩餘可售票券數
    public Map<Integer, Integer> getToSellTQty(Integer activityNo, Integer sessionNo){
        List<BlockPrice> blockList = blockPriceService.getBlockPriceByActivityNo(activityNo);
        Map<Integer, Integer> returnedMap = new HashMap<Integer, Integer>();
        for(BlockPrice block: blockList){
            int toSellTQty = sessionRepository.getToSellNumber(sessionNo);
            returnedMap.put(block.getBlockNo(), toSellTQty);
        }

        System.out.println(returnedMap);
        return returnedMap;
    }
}
