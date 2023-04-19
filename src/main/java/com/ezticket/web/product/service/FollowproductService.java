package com.ezticket.web.product.service;

import com.ezticket.web.product.pojo.Followproduct;
import com.ezticket.web.product.pojo.FollowproductPK;
import com.ezticket.web.product.repository.FollowproductDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


	//刪除單一追蹤
	@Transactional
	public boolean deleteOneProductFollowing(Integer memberno, Integer productno) {
		FollowproductPK followProductPKVO = new FollowproductPK();
		followProductPKVO.setMemberno(memberno);
		followProductPKVO.setProductno(productno);
		Followproduct followProductVO = dao.getByPrimaryKey(followProductPKVO);
		return dao.delete(followProductVO);
	}

	public List<Followproduct> getFollowProductByMemberno(Integer memberno) {
		List<Followproduct> followproductList =dao.getFollowProductByMemberno(memberno);
		System.out.println(followproductList);
		return  followproductList;


	}

	public Set<Integer> getFollowerByProduct(Integer productno) {
		List<Followproduct> followproductList = dao.getFollowProductByProductno(productno);
		Set<Integer> set = new HashSet<>();
		for (Followproduct followproduct : followproductList) {
			set.add(followproduct.getFollowproductPK().getMemberno());
		}
		return set;


	}

	//刪除所有追蹤
	@Transactional
	public boolean deleteFollowProductByMemberno(Integer memberno){
		List<Followproduct> followproductList = dao.getFollowProductByMemberno(memberno);
		for(Followproduct followproduct :followproductList){
			dao.delete(followproduct);
		}
		return true;
	}


}
