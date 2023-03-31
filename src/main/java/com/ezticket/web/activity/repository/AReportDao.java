package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.AReport;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface AReportDao {
    List<AReport> getByCompositeQuery(Map map);
}
