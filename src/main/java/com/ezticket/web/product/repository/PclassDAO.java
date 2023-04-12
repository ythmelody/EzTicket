package com.ezticket.web.product.repository;

import com.ezticket.core.repository.CoreDAO;
import com.ezticket.web.product.pojo.Pclass;
import com.ezticket.web.product.pojo.Product;

import java.util.List;
import java.util.Map;


public interface PclassDAO extends CoreDAO<Pclass,Integer> {
//	public void insert(PclassVO pclassVO);
//    public void update(PclassVO pclassVO);
    public boolean delete(Integer pclassno);
    public List<Pclass>getByPclasstName(String pname);
    public List<Pclass> getByCompletePclasstName(String pclassname);
//    public PclassVO findByPrimaryKey(Integer pclassno);
//    public List<PclassVO> getAll();

}
