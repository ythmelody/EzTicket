package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.CollectVO;

import java.util.List;

public interface CollectDAO {
    void insert(CollectVO collectVO);

    void update(CollectVO collectVO);

    void delete(Integer collectno);

    CollectVO findByPK(Integer collectno);

    List<CollectVO> getAll();
}
