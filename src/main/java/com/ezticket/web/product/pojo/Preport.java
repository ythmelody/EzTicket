package com.ezticket.web.product.pojo;

import java.sql.Timestamp;

import com.ezticket.core.pojo.Core;
import com.ezticket.web.users.pojo.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok: Gene getter/setter by @getter/@setter
@Entity
@Table(name ="PREPORT")
public class Preport extends Core {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer preportno;
	private Integer pcommentno;
	private Integer memberno;
	private String pwhy;
	private Integer preportstatus;
	private Timestamp preportdate;
	@ManyToOne
	@JoinColumn(name ="memberno",insertable = false,updatable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(name ="pcommentno",insertable = false,updatable = false)
	private Pcomment pcomment;
		
	public Preport() {}
	
	
	public Integer getPreportno() {
		return preportno;
	}
	public void setPreportno(Integer preportno) {
		this.preportno = preportno;
	}
	public Integer getPcommentno() {
		return pcommentno;
	}
	public void setPcommentno(Integer pcommentno) {
		this.pcommentno = pcommentno;
	}
	public Integer getMemberno() {
		return memberno;
	}
	public void setMemberno(Integer memberno) {
		this.memberno = memberno;
	}
	public String getPwhy() {
		return pwhy;
	}
	public void setPwhy(String pwhy) {
		this.pwhy = pwhy;
	}
	public Integer getPreportstatus() {
		return preportstatus;
	}
	public void setPreportstatus(Integer preportstatus) {
		this.preportstatus = preportstatus;
	}
	public Timestamp getPreportdate() {
		return preportdate;
	}
	public void setPreportdate(Timestamp preportdate) {
		this.preportdate = preportdate;
	}
	
	

}
