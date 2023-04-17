package com.ezticket.web.activity.service;

import com.ezticket.web.activity.repository.SeatsModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatsModelService {
    @Autowired
    private SeatsModelRepository seatsModelRepository;
    //    由 blockno 查出 modelname、blockname 和 SeatsModel
//    更新 seatsModel 狀態，所有個別座位更新做一筆交易
}
