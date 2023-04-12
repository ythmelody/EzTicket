package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.CollectVO;
import com.ezticket.web.activity.pojo.TicketHolder;
import com.ezticket.web.activity.repository.CollectDAO;
import com.ezticket.web.activity.repository.TicketHolderDAO;
import com.ezticket.web.activity.repository.impl.CollectDAOImpl;
import com.ezticket.web.activity.repository.impl.TicketHolderDAOImpl;

import java.util.List;

public class CollectService {
    private CollectDAO cdao;
    private TicketHolderDAO thdao;

    public CollectService() {

        cdao = new CollectDAOImpl();
        thdao = new TicketHolderDAOImpl();

    }

//    欠會員的修改功能
//    public CollectVO updateCollect(Integer collectno, String memail) {
//
// Step1:  用 email 查出會員編號
//
//
// Step2: 更新 collect 表格中的會員編號
//        CollectVO collectVO = new CollectVO();
//
//        cdao.update(collectVO);
//
//        return cdao.findByPK(collectno);
//    }


    public TicketHolder getOneTicket(Integer collectno) {
        return thdao.findByCollectno(collectno);
    }

    public List<TicketHolder> getAll() {
        return thdao.getAll();
    }
}
