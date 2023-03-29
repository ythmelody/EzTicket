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
    @Column(name = "ACOMMENTNO", insertable=false, updatable=false)
    private Integer aCommentNo;
    @Column(name = "MEMBERNO", insertable=false, updatable=false)
    private Integer memberNo;
    @Column(name = "AWHY")
    private String aWhy;
    @Column(name = "AREPORTSTATUS")
    private Integer aReportStatus;
    @Column(name = "AREPORTDATE")
    private Date aReportDate;

    @ManyToOne
    @JoinColumn(name = "MEMBERNO")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ACOMMENTNO")
    private AComment aComment;
}
