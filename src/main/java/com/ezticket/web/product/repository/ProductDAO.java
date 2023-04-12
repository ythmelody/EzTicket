package com.ezticket.web.product.repository;

import java.util.List;
import java.util.Map;

import com.ezticket.core.repository.CoreDAO;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.util.PageResult;


public interface ProductDAO extends CoreDAO<Product,Integer> {
	public void insert(Product product);
    public void update(Product product);
    public Product getByPrimaryKey(Integer productno);
    @Override
    public List<Product> getAll();


    public List<Product> getAll(Map<String, String[]> map);
    public List<Product>findByProductName(String pname);

    public PageResult<Product> getAll(Map<String, String[]> map, Integer pageNumber, Integer pageSize);
	

}
