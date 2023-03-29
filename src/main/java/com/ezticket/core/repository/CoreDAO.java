package com.ezticket.core.repository;


import com.ezticket.core.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public interface CoreDAO <V ,I> {
	
	void insert(V vo);
	
	void update(V vo);
	
	V getByPrimaryKey(I pk);
	
	List<V> getAll();
	
	default Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	

}
