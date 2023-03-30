package com.ezticket.web.product.repository;

import java.util.List;

import com.ezticket.web.product.pojo.Followproduct;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class FollowproductDAOImpl implements FollowproductDAO {
	@PersistenceContext
	private Session session;

	@Override
	public void insert(Followproduct followproductVO) {
		session.persist(followproductVO);
	}

	@Override
	public void update(Followproduct followproductVO) {
		// TODO �Pı�Τ���o�@�ӥ\��...

	}

	@Override
	public Followproduct getByPrimaryKey(Integer pk) {
		// TODO �o�@�ӬO�ƦX�D��Ӧp��B�z?
		return null;
	}

	@Override
	public List<Followproduct> getAll() {
		final String hql = "FROM Followproduct";
		return session.createQuery(hql, Followproduct.class).getResultList();
	}

	@Override
	public boolean delete(Followproduct FollowproductVO) {
		session.remove(FollowproductVO);
		return true;

	}

	@Override
	public List<Integer> getByMemberno(Integer memberno) {
		// TODO �쥻�O�Q�^��ProductVO���ݭn�B�zjoin�����D�A�٬O�^��FollowproductVO?
		return null;
	}

}
