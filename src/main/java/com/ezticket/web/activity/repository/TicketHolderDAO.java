package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.TicketHolder;

import java.util.List;

public interface TicketHolderDAO {
    TicketHolder findByCollectno(Integer collectno);
    List<TicketHolder> getByMemberno(Integer memberno);

    List<TicketHolder> getAll();
}
