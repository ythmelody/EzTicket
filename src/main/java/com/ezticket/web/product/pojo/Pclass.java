package com.ezticket.web.product.pojo;


import com.ezticket.core.pojo.Core;
import jakarta.persistence.*;

@Entity
@Table(name ="PCLASS")
public class Pclass extends Core {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pclassno;
	private String pclassname;
	
	public Pclass() {}
	
	public Integer getPclassno() {
		return pclassno;
	}
	public void setPclassno(Integer pclassno) {
		this.pclassno = pclassno;
	}
	public String getPclassname() {
		return pclassname;
	}
	public void setPclassname(String pclassname) {
		this.pclassname = pclassname;
	}

}
