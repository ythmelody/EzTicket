package com.ezticket.web.activity.pojo;

import com.ezticket.web.users.pojo.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Torder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "torderno")
	private Integer torderNo;
	@Column(name = "memberno")
	private Integer memberNo;
	@Column
    private Integer ttotal;
	@Column(name = "tdiscounttotal")
    private Integer tdiscountTotal;
	@Column(name = "tcoupontotal")
    private Integer tcouponTotal;
	@Column(name = "tchecktotal")
	private Integer tcheckTotal;
	@Column(name = "tcouponno")
	private Integer tcouponNo;
	@Column(name = "torderdate")
	@JsonFormat(pattern = "yyyy/MM/dd kk:mm:ss", timezone = "GMT+8")
	private Date torderDate;
	@Column(name = "tpaydate")
	private Calendar tpayDate;
	@Column(name = "treceivedate")
	private Calendar treceiveDate;
	@Column(name = "tpaymentstatus")
	private String tpaymentStatus;
	@Column(name = "tprocessstatus")
	private String tprocessStatus;

	@ManyToOne
	@JoinColumn(name = "MEMBERNO" ,insertable = false, updatable = false)
	private Member member;


}
