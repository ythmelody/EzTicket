package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.ActivityDto;
import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.repository.ActivityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ActivityDto> findAllByOrderByActivityNoDesc(){
        return activityRepository.findAllByOrderByActivityNoDesc()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    public Optional<ActivityDto> findByaName(String aName){
        return activityRepository.findByaName(aName).map(this::entityToDTO);

    }



        @Scheduled(cron = "0 0 * * * *")
    public void checkExpiredActivity() {
        LocalDate today = LocalDate.now();
        List<Activity> offStatus = activityRepository.findExpiredActivity(today);

        for (Activity activity : offStatus) {
            activity.setAStatus(2);
            activityRepository.save(activity);
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void checkActiveActivity() {
        LocalDate today = LocalDate.now();
        List<Activity> onStatus = activityRepository.findActiveActivity(today);

        for (Activity activity : onStatus) {
            activity.setAStatus(1);
            activityRepository.save(activity);
        }
    }

    private ActivityDto entityToDTO(Activity activity){

        ActivityDto activityDto = modelMapper.map(activity,ActivityDto.class);
        activityDto.setActivityNo(activity.getActivityNo());
        return activityDto;
    }

    // Add by Shawn on 4/3
    public List<Activity> getAllActAfterSell(){
        return activityRepository.getAllAfterSellDate();
    }

    public List<Activity> getAllActBeforeSell(){ return activityRepository.getAllBeforeSellDate(); }

    public List<Activity> getAllActSelling(){ return activityRepository.getAllBetweenSellDate(); }

    public Optional<Activity> getActById(Integer actNo){ return activityRepository.findById(actNo);}

    public List<Activity> getActByBlurActName(String aname){
        return activityRepository.getByBlurActName(aname);
    }


    public Optional<ActivityDto> findByactivityNo(Integer activityNo) {
        return activityRepository.findByactivityNo(activityNo).map(this::entityToDTO);

    }
    public List<ActivityDto> findAllByActivityNo(Integer activityNo){

        return activityRepository.findAllByActivityNo(activityNo)
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    public Activity saveActivity(Activity activity) {
        Activity activity1 = activityRepository.save(activity);
        activity1.setActivityNo(activityRepository.findLastInsert());
        return activity1;
    }


    public void updateActivity(Activity activity) {
        activityRepository.update(activity.getActivityNo(), activity.getAName(), activity.getAClassNo(), activity.getPerformer(), activity.getHostNo(), activity.getADiscrip(), activity.getANote(), activity.getATicketRemind(), activity.getAPlace(), activity.getAPlaceAdress(), activity.getASDate(), activity.getAEDate(), activity.getWetherSeat(), activity.getASeatsImg());
    }


    public void deleteActivity(Integer activityNo, Integer aStatus) {
            activityRepository.deleteActivity(activityNo,aStatus);

    }
//    public Activity updateActivityById(Integer activityNo, Activity activity) {
//        Optional<Activity> optionalActivity = activityRepository.findById(activityNo);
//
//        if (optionalActivity.isPresent()) {
//            Activity activityObj = optionalActivity.get();
//            activityObj.setAName(activity.getAName());
//            activityObj.setAClassNo(activity.getAClassNo());
//            activityObj.setPerformer(activity.getPerformer());
//            activityObj.setHostNo(activity.getHostNo());
//            activityObj.setADiscrip(activity.getADiscrip());
//            activityObj.setANote(activity.getANote());
//            activityObj.setATicketRemind(activity.getATicketRemind());
//            activityObj.setAPlace(activity.getAPlace());
//            activityObj.setAPlaceAdress(activity.getAPlaceAdress());
//            activityObj.setASDate(activity.getASDate());
//            activityObj.setAEDate(activity.getAEDate());
//            activityObj.setWetherSeat(activity.getWetherSeat());
//            activityObj.setAStatus(activity.getAStatus());
//
//            // Save updated activity object to database
//            return activityRepository.save(activityObj);
//        } else {
//            throw new RuntimeException("Activity with id " + activityNo + " not found");
//        }
//    }


    //    Add by Shawn on 04/19
    public List<Integer> findActNosByWetherSeatIsTrue(){
        return activityRepository.findActNosByWetherSeatIsTrue();
    }
}
