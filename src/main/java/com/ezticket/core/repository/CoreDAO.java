package com.ezticket.core.repository;


import java.util.List;


public interface CoreDAO <V ,I> {

    void insert(V vo);

    Boolean update(V vo);

    V getByPrimaryKey(I pk);

    List<V> getAll();

//	default Session getSession() {
//		return HibernateUtil.getSessionFactory().getCurrentSession();
//	}


}