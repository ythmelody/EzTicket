package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.SessionDto;
import com.ezticket.web.activity.pojo.Session;
import com.ezticket.web.activity.repository.SessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ModelMapper modelMapper;

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
        return sessionRepository.updateById(ticketChange, sessionNo);
    }

    //    Add by Shawn on 04/11
    public Optional<SessionDto> findById(Integer sessionNo){
        return sessionRepository.findById(sessionNo).map(this::entityToDTO);
    }

    public Session saveSession(Session session) {
        return  sessionRepository.save(session);
    }
}
