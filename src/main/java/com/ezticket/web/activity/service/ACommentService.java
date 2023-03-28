package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.AComment;
import com.ezticket.web.activity.repository.impl.ACommentDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class ACommentService {
    ACommentDaoImpl dao = new ACommentDaoImpl();

    List<AComment> getAllAComments() {
        List<AComment> list = dao.getAll();
        return list;
    }

    AComment getACommentById(Integer aCommentId) {
        AComment aComment = dao.getById(aCommentId);
        return aComment;
    }

    String updateACommentStatus(AComment aComment){
        if(dao.update(aComment) != 1)
            return "更新失敗";
        else
            return "更新成功";
    }

    String insertAComment(AComment aComment){
        if(dao.insert(aComment) != 1)
            return "新增失敗";
        else
            return "新增成功";
    }


}
