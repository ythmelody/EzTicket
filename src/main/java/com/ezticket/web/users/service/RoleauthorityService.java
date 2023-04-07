package com.ezticket.web.users.service;

import com.ezticket.web.users.repository.RoleauthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleauthorityService {

    @Autowired
    private RoleauthorityRepository roleauthorityRepository;

}
