package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.AReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface AReportRepository extends JpaRepository<AReport, Integer> {

}

