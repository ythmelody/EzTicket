package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Seats;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsRepository  extends JpaRepository<Seats, Integer> {

    public List<Seats> findBySessionNoAndBlockNo(Integer sessionNo, Integer blockNo);

    @Transactional
    @Modifying
    @Query("UPDATE Seats SET blockName=:blockname, realX=:realx, realY=:realy, seatStatus=:seatstatus  WHERE seatNo=:seatno ")
    public int update(@Param("blockname") String blockName,@Param("realx") String realX, @Param("realy") String realY, @Param("seatstatus") int seatStatus, @Param("seatno") int seatNo);

}
