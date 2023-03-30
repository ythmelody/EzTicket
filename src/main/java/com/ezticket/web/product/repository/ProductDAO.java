package com.ezticket.web.product.repository;

import java.util.List;

import com.ezticket.core.repository.CoreDAO;
import com.ezticket.web.product.pojo.Product;


public interface ProductDAO extends CoreDAO<Product,Integer> {
	public void insert(Product product);
    public void update(Product product);
    public Product getByPrimaryKey(Integer productno);
    @Override
    public List<Product> getAll();
    

    public List<Product>findByVO(Product product);
    public List<Product>findByProductName(String pname);
	

}
