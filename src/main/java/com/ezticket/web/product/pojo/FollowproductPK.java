package com.ezticket.web.product.pojo;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FollowproductPK implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer memberno;
	private Integer productno;
	public Integer getMemberno() {
		return memberno;
	}
	public void setMemberno(Integer memberno) {
		this.memberno = memberno;
	}
	public Integer getProductno() {
		return productno;
	}
	public void setProductno(Integer productno) {
		this.productno = productno;
	}
	
	
	
	

}
