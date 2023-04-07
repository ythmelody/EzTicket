package com.ezticket.web.product.repository;

import com.ezticket.core.repository.CoreDAO;
import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.pojo.Product;

import java.util.List;
import java.util.Map;


public interface PcommentDAO extends CoreDAO<Pcomment,Integer> {
	
//	public void insert(PcommentVO pcommentVO);
//   public void update(PcommentVO pcommentVO);
//    public PcommentVO findByPrimaryKey(Integer pcommentno);
    public List<Pcomment> getAllByMemberno(Integer memberno);
    public List<Pcomment> getAllByProductno(Integer productno);
    public boolean delete(Integer pcommentno);

    public boolean update(Integer pcommentno ,Integer pcommentstatus);
    public List<Pcomment> getAll(Map<String, String[]> map) ;
}
