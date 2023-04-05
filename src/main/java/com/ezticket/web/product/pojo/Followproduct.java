
package com.ezticket.web.product.pojo;


import com.ezticket.core.pojo.Core;
//import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name ="FOLLOWPRODUCT")
public class Followproduct extends Core {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private FollowproductPK followproductPK;

	public FollowproductPK getFollowproductPK() {
		return followproductPK;
	}

	public void setFollowproductPK(FollowproductPK followproductPK) {
		this.followproductPK = followproductPK;
	}


//	@Column(insertable=false, updatable=false)
//	private Integer memberno;
//	@Column(insertable=false, updatable=false)
//	private Integer productno;
//
//	public Followproduct() {}
//
//	public Integer getMemberno() {
//		return memberno;
//	}
//	public void setMemberno(Integer memberno) {
//		this.memberno = memberno;
//	}
//	public Integer getProductno() {
//		return productno;
//	}
//	public void setProductno(Integer productno) {
//		this.productno = productno;
//	}
	
	
}