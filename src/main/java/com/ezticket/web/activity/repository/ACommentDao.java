package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.AComment;
import com.ezticket.web.activity.pojo.AReport;

import java.util.List;
import java.util.Map;

public interface ACommentDao {
    List<AComment> getByCompositeQuery(Map map);
}
