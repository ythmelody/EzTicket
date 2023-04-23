package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.SeatsModelDTO;
import com.ezticket.web.activity.pojo.PlaceModel;
import com.ezticket.web.activity.pojo.SeatsModel;
import com.ezticket.web.activity.repository.SeatsModelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatsModelService {
    @Autowired
    private SeatsModelRepository seatsModelRepository;

    public List<SeatsModel> findByBlockno(Integer blockno){
        return seatsModelRepository.findByBlocknoOrderByXAscYAsc(blockno);
    }

    public boolean existOrNot(Integer blockno){
        List<SeatsModel> sm = seatsModelRepository.findByBlocknoOrderByXAscYAsc(blockno);
//        System.out.println(sm);
        if (sm.isEmpty()) {
            System.out.println("收到空值");
            return false;
        }
        System.out.println("有收到東西");
        return true;
    }

    @Transactional
    public boolean insert(SeatsModel seatsModel){
        seatsModelRepository.save(seatsModel);
        return true;
    }

    @Transactional
    public boolean saveSeatsModel(SeatsModelDTO seatsModelDTO){
        Optional<SeatsModel> optSm =seatsModelRepository.findById(seatsModelDTO.getSeatModelno());
        if (optSm.isEmpty()){
            return false;
        }
        SeatsModel seatsModel = optSm.get();
        BeanUtils.copyProperties(seatsModelDTO, seatsModel);
        seatsModelRepository.save(seatsModel);
        return true;
    }

    @Transactional
    public boolean clearSeatsModel(Integer blockno){
        if (seatsModelRepository.deleteByBlockno(blockno) > 0) {
            return true;
        }
        return false;
    }
    //    由 blockno 查出 modelname、blockname 和 SeatsModel
//    更新 seatsModel 狀態，所有個別座位更新做一筆交易
}
