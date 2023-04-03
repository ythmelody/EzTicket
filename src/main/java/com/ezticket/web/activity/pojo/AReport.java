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
@Table(name = "AREPORT")
public class AReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AREPORTNO")
    private Integer aReportNo;
    @Column(name = "ACOMMENTNO")
    private Integer aCommentNo;
    @Column(name = "MEMBERNO")
    private Integer memberNo;
    @Column(name = "AWHY")
    private String aWhy;
    @Column(name = "AREPORTSTATUS")
    private Integer aReportStatus;
    @Column(name = "AREPORTDATE")
    private Date aReportDate;

    public AReport(Integer aCommentNo, Integer memberNo, String aWhy) {
        this.aCommentNo = aCommentNo;
        this.memberNo = memberNo;
        this.aWhy = aWhy;
    }

    @ManyToOne
    @JoinColumn(name = "MEMBERNO", insertable=false, updatable=false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ACOMMENTNO", insertable=false, updatable=false)
    private AComment aComment;
}
