package com.ezticket.web.product.repository.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ezticket.web.product.pojo.Pcomment;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.product.repository.PcommentDAO;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PcommentDAOImpl implements PcommentDAO {
	@PersistenceContext
	private Session session;
	
	private static final String GET_ALL_BY_MemberNO = 
			"SELECT * FROM pcomment WHERE memberno = ? order by pcommentno";

	@Override
	public void insert(Pcomment pcomment) {
		session.persist(pcomment);
	}

	@Override
	public void update(Pcomment pcommentVO) {
		final StringBuilder hql =new StringBuilder().append("UPDATE PcommentVO SET ");
		hql.append("pcommentcont = :pcommentcont,")
		.append("prate = :prate,")
		.append("pcommentdate = :pcommentdate,")
		.append("pcommentstatus = :pcommentstatus,")
		.append("plike = :plike ")
		.append("WHERE pcommentno = :pcommentno");
		
		Query<?>query=session.createQuery(hql.toString());
		query.setParameter("pcommentcont", pcommentVO.getPcommentcont());
		query.setParameter("prate", pcommentVO.getPrate());
		query.setParameter("pcommentdate", pcommentVO.getPcommentdate());
		query.setParameter("pcommentstatus", pcommentVO.getPcommentcont());
		query.setParameter("plike", pcommentVO.getPlike());
		query.setParameter("pcommentno", pcommentVO.getPcommentno());
		query.executeUpdate();
	}
	@Override
	public boolean update(Integer pcommentno ,Integer pcommentstatus) {
		final String hql ="UPDATE Pcomment SET pcommentstatus = :pcommentstatus WHERE pcommentno = :pcommentno";

		Query<?>query=session.createQuery(hql);
		query.setParameter("pcommentstatus", pcommentstatus);
		query.setParameter("pcommentno", pcommentno);
		query.executeUpdate();
		return true;

	}

	@Override
	public List<Pcomment> getAll(Map<String, String[]> map) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Pcomment> criteriaQuery = builder.createQuery(Pcomment.class);
		Root<Pcomment> root = criteriaQuery.from(Pcomment.class);

		List<Predicate> predicateList = new ArrayList<>();

		Set<String> keys = map.keySet();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0]; //why[0]?
			//!"action".equals(key) 要加避免當成where條件傳進來 ?
			if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
				count++;
				predicateList.add(getPredicateForDB(builder, root, key, value));
			}
		}
		criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
		Query query = session.createQuery(criteriaQuery);
		List<Pcomment> pcommentList = query.getResultList();
		return pcommentList;
	}

	public static Predicate getPredicateForDB(CriteriaBuilder builder, Root<Pcomment> root, String columnName, String value) {
		Predicate predicate = null;
		if ("pcommentstatus".equals(columnName) || "productno".equals(columnName)) {
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
		}
		else if ("pname".equals(columnName))  {
			predicate = builder.like(root.get("product").get("pname"), "%" + value + "%");
		}
		return predicate;
	}

	@Override
	public Pcomment getByPrimaryKey(Integer pcommentno) {
		return session.get(Pcomment.class, pcommentno);
	}

	@Override
	public List<Pcomment> getAllByMemberno(Integer memberno) {
		final String hql = "FROM PcommentVO WHERE memberno = :memberno ORDER BY pcommentno";
		return session
				.createQuery(hql, Pcomment.class)
				.setParameter("memberno", memberno)
				.getResultList();

	}





	@Override
	public List<Pcomment> getAll() {
		final String hql ="FROM Pcomment ORDER BY pcommentno";
		return session.createQuery(hql,Pcomment.class).getResultList();
	}

	@Override
	public boolean delete(Integer pcommentno) {
		Pcomment pcommentVO =session.get(Pcomment.class,pcommentno);
		session.remove(pcommentVO);
		return true;
	}

	@Override
	public List<Pcomment> getAllByProductno(Integer productno) {
		final String hql ="FROM Pcomment WHERE productno =:productno ORDER BY plike";
		return session
				.createQuery(hql,Pcomment.class)
				.setParameter("productno",productno)
				.getResultList();
	}


}
