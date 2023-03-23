package com.ezticket.web.activity.pojo;

import com.ezticket.web.users.pojo.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACOMMENT")
public class AComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACOMMENTNO")
    private Integer aCommentNo;
    @Column(name = "ACTIVITYNO", insertable = false, updatable = false)
    private Integer activityNo;
    @Column(name = "MEMBERNO", insertable = false, updatable = false)
    private Integer memberNo;
    @Column(name = "ACOMMENTIMG")
    private byte[] aCommentImg;
    @Column(name = "ACOMMENTCONT")
    private String aCommentCont;
    @Column(name = "ARATE")
    private Integer aRate;
    @Column(name = "ACOMMENTDATE")
    private Date aCommentDate;
    @Column(name = "ACOMMENTSTATUS")
    private Integer aCommentStatus;
    @Column(name = "ALIKE")
    private Integer aLike;

    @ManyToOne
    @JoinColumn(name = "MEMBERNO")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ACTIVITYNO")
    private Activity activity;
}
