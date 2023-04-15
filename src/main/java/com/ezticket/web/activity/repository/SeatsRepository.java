package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Seats;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Integer> {

    public List<Seats> findBySessionNoAndBlockNo(Integer sessionNo, Integer blockNo);

    @Transactional
    @Modifying
    @Query("UPDATE Seats SET blockName=:blockname, realX=:realx, realY=:realy, seatStatus=:seatstatus  WHERE seatNo=:seatno ")
    public int update(@Param("blockname") String blockName,@Param("realx") String realX, @Param("realy") String realY, @Param("seatstatus") int seatStatus, @Param("seatno") int seatNo);

    @Transactional
    @Modifying
    @Query("UPDATE Seats SET seatStatus=:seatstatus  WHERE seatNo=:seatno ")
    public int updateStatus(@Param("seatstatus") int seatStatus, @Param("seatno") int seatNo);

    @Query("SELECT DISTINCT s.blockNo FROM Seats s JOIN Session ss WHERE ss.activityNo = :actNo AND s.blockNo IS NOT NULL")
    public List<Integer> getActBlockHasSeats(@Param("actNo") int actNo);

    @Query("SELECT DISTINCT s.seatNo FROM Seats s WHERE s.sessionNo = :sessionNo AND s.blockNo = :blockNo")
    public List<Integer> getSessionBlockSeatsExist(@Param("sessionNo") int sessionNo, @Param("blockNo") int blockNo);

    @Query("SELECT s.seatNo FROM Seats s WHERE s.blockNo = :blockNo AND s.seatStatus = :seatStatus")
    public List<Integer> getIdsByBlockNoAndSeatStatus(@Param("blockNo") int blockNo, @Param("seatStatus") int seatStatus);

    public List<Seats> findBySessionNo(int sessionNo);

    @Query(value = "SELECT COUNT(seatNo) FROM Seats WHERE seatStatus = 1 AND sessionNo = :sessionNo AND blockNo = :blockNo", nativeQuery = true)
    public int getToSellNumber(@Param("sessionNo") int sessionNo, @Param("blockNo") int blockNo);

    @Query(value = "SELECT COUNT(seatNo) FROM Seats WHERE seatStatus = 2 AND sessionNo = :sessionNo AND blockNo = :blockNo", nativeQuery = true)
    public int getSoldNumber(@Param("sessionNo") int sessionNo, @Param("blockNo") int blockNo);

    @Query("SELECT count (seatNo) FROM Seats")
    public int getSeatsNumber();

    @Modifying
    @Transactional
    public int deleteBySessionNoAndBlockNo(Integer sessionNo, Integer blockNo);

}
