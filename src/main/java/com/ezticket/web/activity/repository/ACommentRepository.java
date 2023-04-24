package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.AComment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ACommentRepository extends JpaRepository<AComment, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE AComment SET aCommentStatus = :statusNo WHERE aCommentNo = :commentId")
    public int update(@Param("commentId") int commentId, @Param("statusNo") int statusNo);

    @Transactional
    @Modifying
    @Query("UPDATE AComment SET aLike = aLike + :alike WHERE aCommentNo = :commentId")
    public int updateALike(@Param("alike") int aLike, @Param("commentId") int commentId);

    @Query("SELECT ac FROM AComment ac JOIN ac.activity act WHERE act.aName LIKE %?1%")
    public List<AComment> getACommentByActName(@Param("1") String actName);

    @Query("SELECT DISTINCT act.activityNo, act.aName FROM AComment ac JOIN ac.activity act")
    public List getACommentANames();

    public List<AComment> getACommentByActivityNo(Integer actNo);

    public AComment getACommentByMemberNoAndActivityNo(Integer memberNo, Integer activityNo);

}
