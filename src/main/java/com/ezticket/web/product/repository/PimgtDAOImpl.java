package com.ezticket.web.product.repository;

import java.util.List;

import com.ezticket.web.product.pojo.Pimgt;
import org.hibernate.Session;

public class PimgtDAOImpl implements PimgtDAO {

	@Override
	public void insert(Pimgt pimgvo) {
		getSession().persist(pimgvo);

	}

	@Override
	public void update(Pimgt pimgvo) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pimgt getByPrimaryKey(Integer pimgno) {
		return getSession().get(Pimgt.class, pimgno);
	}

	@Override
	public List<Pimgt> getAll() {
		final String hql = "FROM Pimgt ORDER BY pimgno";
		return getSession().createQuery(hql, Pimgt.class).getResultList();
	}

	@Override
	public boolean delete(Integer pimgno) {
		Session session = getSession();
		Pimgt pimgt = session.get(Pimgt.class, pimgno);
		session.remove(pimgt);
		return true;
	}

	@Override
	public List<Pimgt> getAllByProductNo(Integer productno) {
		final String hql = "FROM Pimgt WHERE productno = :productno ORDER BY pimgno";
		return getSession()
				.createQuery(hql,Pimgt.class)
				.setParameter("productno", productno)
				.getResultList();
	}

}
