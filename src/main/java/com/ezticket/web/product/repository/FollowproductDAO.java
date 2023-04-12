package com.ezticket.web.product.repository;

import com.ezticket.core.repository.CoreDAO;
import com.ezticket.web.product.pojo.Followproduct;
import com.ezticket.web.product.pojo.FollowproductPK;

import java.util.List;


public interface FollowproductDAO extends CoreDAO<Followproduct,Integer> {
	public boolean delete(Followproduct followproductVO);
	

	public List<Followproduct> getFollowProductByMemberno(Integer memberno);

	public Followproduct getByPrimaryKey(FollowproductPK followproductPK);


}
