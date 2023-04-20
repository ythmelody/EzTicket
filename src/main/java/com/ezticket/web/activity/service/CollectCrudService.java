package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.Collect;
import com.ezticket.web.activity.pojo.Tdetails;
import com.ezticket.web.activity.pojo.TorderDetailsView;
import com.ezticket.web.activity.repository.CollectRepository;
import com.ezticket.web.activity.repository.TicketHolderDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CollectCrudService {
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private TorderDetailsViewService torderDetailsViewService;
    CollectService collectService = new CollectService();

    //    新增
    public boolean insertCollect(Tdetails tdetails) {
//      取訂單新增後的 tdetailsNo
//      用 tdetailsNo 查 TorderDetailsView 中的 memberno
        Integer tdetailsno = tdetails.getTdetailsNo();
        Optional<TorderDetailsView> newOD = torderDetailsViewService.getOne(tdetailsno);
        if (newOD.isEmpty()) {
            System.out.println("票券新增失敗，未取得訂單明細");
            return false;
        }
        Integer memberno = newOD.get().getMemberNo();
        Collect newCollect = new Collect();
        newCollect.setMemberno(memberno);
        newCollect.setTdetailsno(tdetailsno);
        newCollect.setTstatus(0);
        collectRepository.save(newCollect);

//      新增進 Redis tStatus 及 控制器 QRcode 進入 Redis

        return true;
    }

    @Transactional
//  更變票券狀態(-1 取消；0 未使用；1 已使用)
    public boolean changeStatus(Integer collectno, Integer gstatus) {
        Optional<Collect> optC = collectRepository.findById(collectno);
        if (optC.isEmpty()) {
            System.out.println("查無票券");
            return false;
        }
        Collect newCollect = optC.get();
        newCollect.setTstatus(gstatus);
        collectRepository.save(newCollect);
        return true;
    }

    @Transactional
    //    使用票券
    public boolean useTicket(Integer collectno){
//        修改 Redis 的 tStatus
        return true;
    }

    @Transactional
//    取消訂單
    public boolean cancelOrder(Integer torderNo){
//        查詢出所有明細編號
        List<TorderDetailsView> canceledDetails = torderDetailsViewService.findAllBytorderNo(torderNo);
        List<Integer> cDetailsno = new ArrayList<Integer>();
        for (TorderDetailsView d:canceledDetails){
//         查出一筆明細編號
            Integer dno = d.getTdetailsNo();
//         查出一筆票券編號
            Integer cno = collectRepository.findByTdetailsno(dno).getCollectno();
            boolean mysqlResult = changeStatus(cno, -1);
            System.out.println("MySQL 已改變票券狀態"+mysqlResult);
//  刪除 Redis 的該筆票券

        }
    return true;
}
}


