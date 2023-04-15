package com.ezticket.web.product.repository;

import java.util.Set;

public interface PcommentRedisDAO<String> {
    public Integer addKeyValue(java.lang.String key, java.lang.String value);

    public Integer deleteKeyValue(java.lang.String key ,java.lang.String value);

    public Set<String> findAllValues(java.lang.String key);

}
