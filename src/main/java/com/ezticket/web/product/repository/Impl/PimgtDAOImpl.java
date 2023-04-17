package com.ezticket.web.product.repository.Impl;

import java.util.List;

import com.ezticket.web.product.pojo.Pimgt;
import com.ezticket.web.product.repository.PimgtDAO;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class PimgtDAOImpl implements PimgtDAO {
	@PersistenceContext
	private Session session;
	@Override
	public void insert(Pimgt pimgt) {
		session.persist(pimgt);

	}

	@Override
	public Boolean update(Pimgt pimgvo) {
		// TODO Auto-generated method stub

        return null;
    }

	@Override
	public Pimgt getByPrimaryKey(Integer pimgno) {
		return session.get(Pimgt.class, pimgno);
	}

	@Override
	public List<Pimgt> getAll() {
		final String hql = "FROM Pimgt ORDER BY pimgno";
		return session.createQuery(hql, Pimgt.class).getResultList();
	}

	@Override
	public boolean delete(Integer pimgno) {
		Pimgt pimgt = session.get(Pimgt.class, pimgno);
		session.remove(pimgt);
		return true;
	}

	@Override
	public List<Pimgt> getAllByProductNo(Integer productno) {
		final String hql = "FROM Pimgt WHERE productno = :productno ORDER BY pimgno";
		return session
				.createQuery(hql,Pimgt.class)
				.setParameter("productno", productno)
				.getResultList();
	}

}
