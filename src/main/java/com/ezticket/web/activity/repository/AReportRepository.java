package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.AReport;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AReportRepository extends JpaRepository<AReport, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE AReport SET aReportStatus = :statusNo WHERE aReportNo = :reportId")
    public int update(@Param("reportId") int reportId, @Param("statusNo") int statusNo);

    @Query("SELECT ar FROM AReport ar JOIN ar.aComment ac JOIN ac.activity act WHERE act.aName LIKE %?1%")
    public List<AReport> getAReportByActName(@Param("1") String actName);

}

