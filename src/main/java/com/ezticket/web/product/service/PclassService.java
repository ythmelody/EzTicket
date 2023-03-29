package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Pclass;
import com.ezticket.web.product.repository.PclassDAO;
import com.ezticket.web.product.repository.PclassDaoImpl;

import java.util.List;

public class PclassService {

	private PclassDAO dao;

	public PclassService() {
		dao = new PclassDaoImpl();
	}

	public Pclass addProductClass(String pclassname) {
		Pclass pclassVO = new Pclass();
		pclassVO.setPclassname(pclassname);
		dao.insert(pclassVO);
		return pclassVO;
	}

	public Pclass updateProductClass(Pclass pclassVO) {
		final Pclass oldPclassVO = dao.getByPrimaryKey(pclassVO.getPclassno());
		pclassVO.setPclassno(oldPclassVO.getPclassno());
		dao.update(pclassVO);
		return pclassVO;
	}

	public Pclass getOneProductClass(Integer pclassno) {
		return dao.getByPrimaryKey(pclassno);
	}

	public List<Pclass> getAllProductClass() {
		return dao.getAll();
	}
	
	public boolean deleteProductClass(Integer pclassno) {
		return dao.delete(pclassno);
	}
}
