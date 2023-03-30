package com.ezticket.web.product.pojo;

import com.ezticket.core.pojo.Core;
import jakarta.persistence.*;

@Entity
@Table(name ="PIMGT")
public class Pimgt extends Core {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer  pimgno;
	private Integer  productno;
	private byte[]  pimg;
	
	public Pimgt() {}
	
	public Integer getPimgno() {
		return pimgno;
	}
	public void setPimgno(Integer pimgno) {
		this.pimgno = pimgno;
	}
	public Integer getProductno() {
		return productno;
	}
	public void setProductno(Integer productno) {
		this.productno = productno;
	}
	public byte[] getPimg() {
		return pimg;
	}
	public void setPimg(byte[] pimg) {
		this.pimg = pimg;
	}
	
	

}
