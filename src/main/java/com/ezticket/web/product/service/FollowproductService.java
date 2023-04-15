package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Followproduct;
import com.ezticket.web.product.pojo.FollowproductPK;
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
	public Boolean addFollowProduct(Integer memberno, Integer productno) {
		FollowproductPK followProductPK =new FollowproductPK();
		followProductPK.setMemberno(memberno);
		followProductPK.setProductno(productno);
		Followproduct followProduct =new Followproduct();
		followProduct.setFollowproductPK(followProductPK);
		dao.insert(followProduct);
		return true;
	}
	

	
	public List<Followproduct> getAllProductFollowing(){
		return dao.getAll();
	}


	@Transactional
	public boolean deleteOneProductFollowing(Integer memberno, Integer productno) {
		FollowproductPK followProductPKVO = new FollowproductPK();
		followProductPKVO.setMemberno(memberno);
		followProductPKVO.setProductno(productno);
		Followproduct followProductVO = dao.getByPrimaryKey(followProductPKVO);
		return dao.delete(followProductVO);
	}

	public List<Followproduct> getFollowProductByMemberno(Integer memberno) {
		return dao.getFollowProductByMemberno(memberno);

	}


}
