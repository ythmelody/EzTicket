package com.ezticket.web.product.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ezticket.web.product.pojo.Pcomment;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PcommentDAOImpl implements PcommentDAO {
	@PersistenceContext
	private Session session;
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/db01?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "";
	
	private static final String GET_ALL_BY_MemberNO = 
			"SELECT * FROM pcomment WHERE memberno = ? order by pcommentno";

	@Override
	public void insert(Pcomment pcommentVO) {
		session.persist(pcommentVO);
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
	public Pcomment getByPrimaryKey(Integer pcommentno) {
		return session.get(Pcomment.class, pcommentno);
	}

	@Override
	public List<Pcomment> getAllByMemberno(Integer memberno) {
		final String hql ="FROM PcommentVO WHERE memberno = :memberno ORDER BY pcommentno";
		return session
				.createQuery(hql,Pcomment.class)
				.setParameter("memberno",memberno)
				.getResultList();
		
//		List<PcommentVO> list = new ArrayList<PcommentVO>();
//		PcommentVO pcommentVO = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ALL_BY_MemberNO);
//			
//			pstmt.setInt(1, memberno);
//			
//			rs = pstmt.executeQuery();
//			
//			while (rs.next()) {
//				pcommentVO = new PcommentVO();
//				pcommentVO.setPcommentno(rs.getInt("pcommentno"));
//				pcommentVO.setProductno(rs.getInt("productno;"));
//				pcommentVO.setPcommentcont(rs.getString("pcommentcont"));
//				pcommentVO.setPrate(rs.getInt("prate"));
//				pcommentVO.setPcommentdate(rs.getTimestamp("pcommentdate"));
//				pcommentVO.setMemberno(rs.getInt("memberno"));
//				pcommentVO.setPcommentstatus(rs.getInt("pcommentstatus;"));
//				pcommentVO.setPlike(rs.getInt("plike"));
//				list.add(pcommentVO);
//			}
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		return list;
	}

	
	public List<Pcomment> getAll(Map<String, String[]> map) {
		List<Pcomment> list = new ArrayList<Pcomment>();
		Pcomment pcommentVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String GET_ALL_BY_Filter = "SELECT * FROM pcomment";
		

		boolean first_condition = true;
		if (map.containsKey("memberno")) {

			if (first_condition) {
				GET_ALL_BY_Filter += " WHERE";

				first_condition = false;
			} else {
				GET_ALL_BY_Filter += " AND";
			}
			for(String s:map.get("memberno")) {
				GET_ALL_BY_Filter += " memberno=" + s;
			}
		}
		if (map.containsKey("productno")) {

			if (first_condition) {
				GET_ALL_BY_Filter += " WHERE";

				first_condition = false;
			} else {
				GET_ALL_BY_Filter += " AND";
			}
			for(String s:map.get("productno")) {
				GET_ALL_BY_Filter += " productno=" + s;
			}
		}
		GET_ALL_BY_Filter += " order by pcommentno";
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_BY_Filter);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				pcommentVO = new Pcomment();
				pcommentVO.setPcommentno(rs.getInt("pcommentno"));
				pcommentVO.setProductno(rs.getInt("productno;"));
				pcommentVO.setPcommentcont(rs.getString("pcommentcont"));
				pcommentVO.setPrate(rs.getInt("prate"));
				pcommentVO.setPcommentdate(rs.getTimestamp("pcommentdate"));
				pcommentVO.setMemberno(rs.getInt("memberno"));
				pcommentVO.setPcommentstatus(rs.getInt("pcommentstatus;"));
				pcommentVO.setPlike(rs.getInt("plike"));
				list.add(pcommentVO);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
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
