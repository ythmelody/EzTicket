package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.TorderDetailsView;
import com.ezticket.web.activity.repository.TorderDetailsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TorderDetailsViewService {

    @Autowired
    private TorderDetailsViewRepository torderDetailsViewRepository;
    public List<TorderDetailsView> findAllBytorderNo(Integer torderNo){
        return torderDetailsViewRepository.findAllBytorderNo(torderNo)
                .stream().collect(Collectors.toList());
    }

    public Optional<TorderDetailsView> getOne(Integer tdetailsNo){
        return torderDetailsViewRepository.findById(tdetailsNo);
    }
}