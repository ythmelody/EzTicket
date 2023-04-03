package com.ezticket.web.product.pojo;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name ="PCOMMENT")
public class Pcomment implements java.io.Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pcommentno;
	private Integer productno;
//	private Byte[]  pcommentimg;
	private String pcommentcont;
	private Integer prate;
	private Timestamp pcommentdate;
	private Integer memberno;
	private Integer pcommentstatus;
	private Integer plike;
	
	public Pcomment() {}
	
	public Integer getPcommentno() {
		return pcommentno;
	}
	public void setPcommentno(Integer pcommentno) {
		this.pcommentno = pcommentno;
	}
	public Integer getProductno() {
		return productno;
	}
	public void setProductno(Integer productno) {
		this.productno = productno;
	}
//	public Byte[] getPcommentimg() {
//		return pcommentimg;
//	}
//	public void setPcommentimg(Byte[] pcommentimg) {
//		this.pcommentimg = pcommentimg;
//	}
	public String getPcommentcont() {
		return pcommentcont;
	}
	public void setPcommentcont(String pcommentcont) {
		this.pcommentcont = pcommentcont;
	}
	public Integer getPrate() {
		return prate;
	}
	public void setPrate(Integer prate) {
		this.prate = prate;
	}
	public Timestamp getPcommentdate() {
		return pcommentdate;
	}
	public void setPcommentdate(Timestamp pcommentdate) {
		this.pcommentdate = pcommentdate;
	}
	public Integer getMemberno() {
		return memberno;
	}
	public void setMemberno(Integer memberno) {
		this.memberno = memberno;
	}
	public Integer getPcommentstatus() {
		return pcommentstatus;
	}
	public void setPcommentstatus(Integer pcommentstatus) {
		this.pcommentstatus = pcommentstatus;
	}
	public Integer getPlike() {
		return plike;
	}
	public void setPlike(Integer plike) {
		this.plike = plike;
	}
	
	

}
