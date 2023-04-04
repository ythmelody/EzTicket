package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Pclass;
import com.ezticket.web.product.repository.PclassDAO;
import com.ezticket.web.product.repository.Impl.PclassDaoImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PclassService {
@Autowired
	private PclassDAO dao;

	@Transactional
	public Pclass addProductClass(String pclassname) {
		Pclass pclass = new Pclass();
		pclass.setPclassname(pclassname);
		dao.insert(pclass);
		return pclass;
	}

	@Transactional
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

	@Transactional
	public boolean deleteProductClass(Integer pclassno) {
		return dao.delete(pclassno);
	}
}
