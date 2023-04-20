package com.ezticket.core.listener;

import com.ezticket.web.activity.repository.SeatsRepository;
import com.ezticket.web.activity.repository.SessionRepository;
import com.ezticket.web.activity.service.SeatsService;
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

    @Autowired
    private SeatsService seatsService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        seatsService.saveSeatsSets();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
