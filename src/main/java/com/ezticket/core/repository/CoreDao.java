package com.ezticket.core.repository;

import java.util.List;

public interface CoreDao<P, I> {
    int insert(P pojo);
    int deleteById(I id);
    int update(P pojo);
    P getById(I id);
    List<P> getAll();

}

