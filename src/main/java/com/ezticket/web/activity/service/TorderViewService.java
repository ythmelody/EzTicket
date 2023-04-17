package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.TorderView;
import com.ezticket.web.activity.repository.TorderViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TorderViewService {

    @Autowired
    private TorderViewRepository torderViewRepository;
    public List<TorderView> findBymemberNo(Integer memberNo){
        return torderViewRepository.findBymemberNo(memberNo)
                .stream().collect(Collectors.toList());
    }
}