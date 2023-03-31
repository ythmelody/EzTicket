package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Pimgt;
import com.ezticket.web.product.repository.PimgtDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PimgtService {
	@Autowired
	private PimgtDAO dao;

	@Transactional
	public Pimgt addProductImg(Integer productno, byte[] pimg) {
		Pimgt pimgt =new Pimgt();
		pimgt.setProductno(productno);
		pimgt.setPimg(pimg);
		dao.insert(pimgt);
		return pimgt;
	}

	public Pimgt getOneProductImg(Integer pimgno) {
		return dao.getByPrimaryKey(pimgno);
	}
	
	public List<Pimgt> getAllImgByProductNo(Integer productno){
		return dao.getAllByProductNo(productno);
	}

	@Transactional
	public boolean deleteProductImg(Integer  pimgno) {
		return dao.delete(pimgno);
	}
	
	public List<Pimgt> getAllProductImg(){
		return dao.getAll();
		
	}

}
