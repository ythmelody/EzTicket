package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Seats;
import com.ezticket.web.activity.pojo.Session;
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

//    public List<Seats> findBySessionNoAndBlockNo(Integer sessionNo, Integer blockNo);

    @Query(value = "SELECT * FROM seats WHERE sessionNo = :sessionNo AND blockNo = :blockNo ORDER BY x asc , y asc;", nativeQuery = true)
    public List<Seats> findBySessionNoAndBlockNo(@Param("sessionNo") Integer sessionNo, @Param("blockNo") Integer blockNo);

    @Transactional
    @Modifying
    @Query("UPDATE Seats SET blockName=:blockname, realX=:realx, realY=:realy, seatStatus=:seatstatus  WHERE seatNo=:seatno ")
    public int update(@Param("blockname") String blockName, @Param("realx") String realX, @Param("realy") String realY, @Param("seatstatus") int seatStatus, @Param("seatno") int seatNo);

    @Transactional
    @Modifying
    @Query("UPDATE Seats SET seatStatus=:seatstatus  WHERE seatNo=:seatno ")
    public int updateStatus(@Param("seatstatus") int seatStatus, @Param("seatno") int seatNo);

    @Query("SELECT DISTINCT s.blockNo FROM Seats s JOIN Session ss WHERE ss.activityNo = :actNo AND s.blockNo IS NOT NULL")
    public List<Integer> getActBlockHasSeats(@Param("actNo") int actNo);

    @Query("SELECT DISTINCT s.seatNo FROM Seats s WHERE s.sessionNo = :sessionNo AND s.blockNo = :blockNo")
    public List<Integer> getSessionBlockSeatsExist(@Param("sessionNo") int sessionNo, @Param("blockNo") int blockNo);

    @Query("SELECT s.seatNo FROM Seats s WHERE s.sessionNo = :sessionNo AND s.blockNo = :blockNo AND s.seatStatus = :seatStatus ORDER BY s.x asc, s.y asc")
    public List<Integer> getIdsBySessionNoANDBlockNoAndSeatStatus(@Param("sessionNo") int sessionNo, @Param("blockNo") int blockNo, @Param("seatStatus") int seatStatus);

    public List<Seats> findBySessionNo(int sessionNo);


    @Query("SELECT s FROM Seats s WHERE s.sessionNo = :sessionNo ORDER BY s.blockNo asc, s.x asc, s.y asc")
    public List<Seats> findOrderedSeatsBySessionNo(@Param("sessionNo") int sessionNo);

    @Query(value = "SELECT COUNT(seatNo) FROM Seats WHERE seatStatus = 1 AND sessionNo = :sessionNo AND blockNo = :blockNo", nativeQuery = true)
    public int getToSellNumber(@Param("sessionNo") int sessionNo, @Param("blockNo") int blockNo);

    @Query(value = "SELECT COUNT(seatNo) FROM Seats WHERE seatStatus = 2 AND sessionNo = :sessionNo AND blockNo = :blockNo", nativeQuery = true)
    public int getSoldNumber(@Param("sessionNo") int sessionNo, @Param("blockNo") int blockNo);

    @Query("SELECT count (seatNo) FROM Seats")
    public int getSeatsNumber();

    @Modifying
    @Transactional
    public int deleteBySessionNoAndBlockNo(Integer sessionNo, Integer blockNo);


    //    Add on 04/19
    //    計算座位席總數量
    @Query("SELECT COUNT(s.seatNo) FROM Seats s WHERE s.x IS NOT NULL AND s.sessionNo = :sessionNo")
    public int findSeatQtyBySessionNo(@Param("sessionNo") Integer sessionNo);

    //    計算站席總數量
    @Query("SELECT COUNT(s.seatNo) FROM Seats s WHERE s.x IS NULL AND s.sessionNo = :sessionNo")
    public int findStandingQtyBySessionNo(@Param("sessionNo") Integer sessionNo);

    //    計算座位席售出總數量(不含保留及場地限制)
    @Query("SELECT COUNT(s.seatNo) FROM Seats s WHERE s.x IS NOT NULL AND s.seatStatus = 2 AND s.sessionNo = :sessionNo")
    public int findSoldSeatQtyBySessionNo(@Param("sessionNo") Integer sessionNo);

    //    計算站席總數量(不含保留及場地限制)
    @Query("SELECT COUNT(s.seatNo) FROM Seats s WHERE s.x IS NULL AND s.seatStatus = 2 AND s.sessionNo = :sessionNo")
    public int findSoldStandingQtyBySessionNo(@Param("sessionNo") Integer sessionNo);

    @Modifying
    @Transactional
    @Query("DELETE FROM Seats WHERE sessionNo = :sessionNo")
    public void deleteBySessionNo(@Param("sessionNo")Integer sessionNo);

    Seats findBySessionNoAndSeatNo(Integer sessionNo, Integer seatNo);

}
