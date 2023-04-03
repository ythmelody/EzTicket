package com.ezticket.web.product.repository;

import com.ezticket.core.repository.CoreDAO;
import com.ezticket.web.product.pojo.Pimgt;

import java.util.List;


public interface PimgtDAO extends CoreDAO<Pimgt, Integer> {
//	public void insert(PimgtVO pimgtVO);
	public boolean delete(Integer pimgno);
	public List<Pimgt> getAllByProductNo(Integer productno);
//	public PimgtVO getByPrimaryKey(Integer pimgno);
}