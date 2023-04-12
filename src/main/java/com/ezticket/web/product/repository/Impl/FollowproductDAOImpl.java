package com.ezticket.web.product.repository.Impl;

import java.util.List;

import com.ezticket.web.product.pojo.Followproduct;
import com.ezticket.web.product.pojo.FollowproductPK;
import com.ezticket.web.product.pojo.Pclass;
import com.ezticket.web.product.repository.FollowproductDAO;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class FollowproductDAOImpl implements FollowproductDAO {
	@PersistenceContext
	private Session session;

	//新增單筆追蹤商品紀錄
	@Override
	public void insert(Followproduct followproduct) {
		session.persist(followproduct);
	}

	@Override
	public void update(Followproduct followproductVO) {
		// TODO 用不到這個方法

	}

	@Override
	public Followproduct getByPrimaryKey(Integer pk) {
		// TODO 用不到這個方法
		return null;
	}

	@Override
	public Followproduct getByPrimaryKey(FollowproductPK followproductPK) {
		// 取得單筆追蹤紀錄(為了確實刪除那筆紀錄)
		return session.get(Followproduct.class,followproductPK);
	}


	@Override
	public List<Followproduct> getAll() {
		final String hql = "FROM Followproduct";
		return session.createQuery(hql, Followproduct.class).getResultList();
	}


	@Override
	public boolean delete(Followproduct Followproduct) {
		session.remove(Followproduct);
		return true;

	}
	@Override
	public List<Followproduct> getFollowProductByMemberno(Integer memberno) {
		final String hql = "FROM Followproduct WHERE followproductPK.memberno = :memberno";
		Query<Followproduct> query =session.createQuery(hql, Followproduct.class);
		query.setParameter("memberno",memberno);
		return query.getResultList();
	}

}
