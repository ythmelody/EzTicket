package com.ezticket.web.users.service;

import com.ezticket.web.users.pojo.Backuser;
import com.ezticket.web.users.pojo.Host;
import com.ezticket.web.users.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostService {
    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private BackuserService backuserService;

    public List<Host> getAllHost(){
        return hostRepository.findAll()
                .stream().toList();
    }

    public Host updateHost(Integer hostno, Host newHost) {
        Optional<Host> originalHostOptional = hostRepository.findById(hostno);
        if (originalHostOptional.isPresent()) {
            Host updateTheHost = originalHostOptional.get(); //先裝入舊資料,下面再逐一設定裝入新資料
            updateTheHost.setHostname(newHost.getHostname());
            updateTheHost.setHostcontact(newHost.getHostcontact());
            updateTheHost.setHostemail(newHost.getHostemail());
            updateTheHost.setHostcell(newHost.getHostcell());
            return hostRepository.save(updateTheHost); //最後,把裝好的新資料存入資料庫中
        } else {
            throw new RuntimeException("Host not found with hostno: " + hostno);
        }
    }

    //如果後台使用者是驗票人員的話,拿取host資料
    public Host getBuHost(String baacount){
        Backuser backuser = backuserService.getBackuserInfo(baacount);
        Host host = hostRepository.findByHostemail(backuser.getBaemail());
        return host;
    }


}
