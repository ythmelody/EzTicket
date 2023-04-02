package com.ezticket.web.product.pojo;


import com.ezticket.core.pojo.Core;
import jakarta.persistence.*;

import java.nio.MappedByteBuffer;
import java.util.List;

@Entity
@Table(name ="PCLASS")
public class Pclass extends Core {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pclassno;
	private String pclassname;

	@Transient  //一定要加，避免重複映射導致資料量過多無法用json傳出去
	@OneToMany(mappedBy = "pclass",fetch = FetchType.EAGER)
	private List<Product> products;
	
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
