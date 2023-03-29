package com.ezticket.web.product.repository;

import com.ezticket.core.repository.CoreDAO;
import com.ezticket.web.product.pojo.Followproduct;

import java.util.List;


public interface FollowproductDAO extends CoreDAO<Followproduct,Integer> {
	public boolean delete(Followproduct followproductVO);
	

	public List<Integer> getByMemberno(Integer memberno);

}
