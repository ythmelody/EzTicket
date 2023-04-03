package com.ezticket.web.users.service;

import com.ezticket.web.users.pojo.Host;
import com.ezticket.web.users.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {
    @Autowired
    private HostRepository hostRepository;
    public List<Host> getAllHost(){
        return hostRepository.findAll()
                .stream().toList();
    }
}
