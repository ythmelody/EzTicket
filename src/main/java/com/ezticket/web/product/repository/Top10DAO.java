package com.ezticket.web.product.repository;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

public interface Top10DAO {

    public void addKeyValue(java.lang.String pname, double sale_num);


    public Set<ZSetOperations.TypedTuple<String>> findAllValues();

    public List<Object[]> search(String keyword, int pageNum, int pageSize);
}
