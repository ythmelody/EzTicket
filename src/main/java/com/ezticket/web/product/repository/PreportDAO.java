package com.ezticket.web.product.repository;

import com.ezticket.core.repository.CoreDAO;
import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.pojo.Preport;
import com.ezticket.web.product.util.PageResult;

import java.util.List;
import java.util.Map;


public interface PreportDAO extends CoreDAO<Preport, Integer> {
    //	public void insert (PreportVO preportVO);
//	public void update (PreportVO preportVO);
//	public List<PreportVO> getAll();
//	public PreportVO getByPrimaryKey(Integer preportno);
    public List<Preport> getAll(Map<String, String[]> map);

    public PageResult<Preport> getAll(Map<String, String[]> map, Integer pageNumber, Integer pageSize);

}
