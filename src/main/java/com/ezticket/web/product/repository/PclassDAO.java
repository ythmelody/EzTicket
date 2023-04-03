package com.ezticket.web.product.repository;

import com.ezticket.core.repository.CoreDAO;
import com.ezticket.web.product.pojo.Pclass;


public interface PclassDAO extends CoreDAO<Pclass,Integer> {
//	public void insert(PclassVO pclassVO);
//    public void update(PclassVO pclassVO);
    public boolean delete(Integer pclassno);
//    public PclassVO findByPrimaryKey(Integer pclassno);
//    public List<PclassVO> getAll();

}
