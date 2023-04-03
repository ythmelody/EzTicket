package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Followproduct;
import com.ezticket.web.product.repository.FollowproductDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowproductService {

	@Autowired
	private FollowproductDAO dao;

	@Transactional
	public Followproduct addFollowProduct(Integer memberno, Integer productno) {
		Followproduct followProductVO = new Followproduct();
		followProductVO.setMemberno(memberno);
		followProductVO.setProductno(productno);
		dao.insert(followProductVO);
		return followProductVO;
	}
	
	public Followproduct getOneProductFollowOfOneMember(Integer memberno, Integer productno) {
		return null;
		
	}
	
	public List<Followproduct> getAllProductFollowing(){
		return dao.getAll();
	}
	
	public boolean deleteOneProductFollowing(Integer memberno, Integer productno) {
		Followproduct followproductVO = new Followproduct();
		followproductVO.setMemberno(memberno);
		followproductVO.setProductno(productno);
		return dao.delete(followproductVO);
	}

}
