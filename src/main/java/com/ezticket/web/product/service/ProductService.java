package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductService {
	@Autowired
	private ProductDAO dao;

	public Product addProduct(Integer pclassno, String pname, Integer hostno, String pdiscrip, Integer pprice,
							  Integer pspecialprice, Integer pqty, Timestamp psdate, Timestamp pedate, String ptag, Integer pstatus) {
		Product productVO = new Product();
		productVO.setPclassno(pclassno);
		productVO.setPname(pname);
		productVO.setHostno(hostno);
		productVO.setPdiscrip(pdiscrip);
		productVO.setPprice(pprice);
		productVO.setPspecialprice(pspecialprice);
		productVO.setPqty(pqty);
		productVO.setPsdate(psdate);
		productVO.setPedate(pedate);
		productVO.setPtag(ptag);
		productVO.setPstatus(pstatus);
		dao.insert(productVO);
		return productVO;
	}

	public Product updateProduct(Integer productno, Integer pclassno, String pname, Integer hostno, String pdiscrip,
			Integer pprice, Integer pspecialprice, Integer pqty, Timestamp psdate, Timestamp pedate, String ptag,
			Integer pstatus, Integer pratetotal, Integer prateqty) {

		Product productVO = new Product();
		productVO.setProductno(productno);
		productVO.setPclassno(pclassno);
		productVO.setPname(pname);
		productVO.setHostno(hostno);
		productVO.setPdiscrip(pdiscrip);
		productVO.setPprice(pprice);
		productVO.setPspecialprice(pspecialprice);
		productVO.setPqty(pqty);
		productVO.setPsdate(psdate);
		productVO.setPedate(pedate);
		productVO.setPtag(ptag);
		productVO.setPstatus(pstatus);
		productVO.setPratetotal(pratetotal);
		productVO.setPrateqty(prateqty);
		dao.update(productVO);

		return productVO;
	}
	
	public Product getOneProduct(Integer productno) {
		return dao.getByPrimaryKey(productno);
	}
	
	public List<Product> getAllProduct(){
		return dao.getAll();
	}
	
	public List<Product> getAllByName(String pname){
		return dao.findByProductName(pname);
	}
	
	
	
}
