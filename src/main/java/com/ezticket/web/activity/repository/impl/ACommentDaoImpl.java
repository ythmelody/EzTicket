package com.ezticket.web.activity.repository.impl;

import com.ezticket.web.activity.pojo.AComment;
import com.ezticket.web.activity.repository.ACommentDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ACommentDaoImpl implements ACommentDao {

    final String DRIVER = "com.mysql.cj.jdbc.Driver";
    final String URL = "jdbc:mysql://localhost:3306/ezticket?serverTimezone=Asia/Taipei";
    final String USERID = "root";
    final String USERPWD = "root";

    private static final String INSERT_STMT =
            "INSERT INTO acomment "
                    + "(activityno, memberno, acommentcont, arate) "
                    + "VALUES (?, ?, ?, ?)";

    @Override
    public int insert(AComment aComment) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERID, USERPWD);
            pstmt = conn.prepareStatement(INSERT_STMT);

            pstmt.setInt(1, aComment.getActivityNo());
            pstmt.setInt(2, aComment.getMemberNo());
            pstmt.setString(3, aComment.getACommentCont());
            pstmt.setInt(4, aComment.getARate());
            pstmt.executeUpdate();

            return 1;

        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
            return 0;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final String DELETE_BY_ID_STMT = "DELETE FROM acomment where acommentno = ?";

    @Override
    public int deleteById(Integer aCommentId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERID, USERPWD);
            pstmt = conn.prepareStatement(DELETE_BY_ID_STMT);
            pstmt.setInt(1, aCommentId);
            pstmt.executeUpdate();
            return 1;

        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
            return 0;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final String UPDATE_STMT =
            "UPDATE acomment "
                    + "SET acommentstatus = ? "
                    + "WHERE acommentno = ?";

    @Override
    public int update(AComment aComment) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERID, USERPWD);
            pstmt = conn.prepareStatement(UPDATE_STMT);

            pstmt.setInt(1, aComment.getACommentStatus());
            pstmt.setInt(2, aComment.getACommentNo());
            pstmt.executeUpdate();

            return 1;
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
            return 0;
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final String FIND_BY_ID_STMT =
            "SELECT * FROM acomment WHERE acommentno = ?";

    @Override
    public AComment getById(Integer aCommentId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERID, USERPWD);
            pstmt = conn.prepareStatement(FIND_BY_ID_STMT);
            pstmt.setInt(1, aCommentId);
            rs = pstmt.executeQuery();

            AComment aComment = new AComment();

            while (rs.next()) {
                aComment.setACommentNo(rs.getInt("acommentno"));
                aComment.setActivityNo(rs.getInt("activityno"));
                aComment.setMemberNo(rs.getInt("memberno"));
                aComment.setACommentCont(rs.getString("acommentcont"));
                aComment.setARate(rs.getInt("arate"));
                aComment.setACommentDate(rs.getDate("acommentdate"));
                aComment.setACommentStatus(rs.getInt("acommentstatus"));
                aComment.setALike(rs.getInt("alike"));
            }

            return aComment;

        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
            return null;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final String GET_ALL_STMT = "SELECT * FROM acomment";

    public List<AComment> getAll() {
        List<AComment> list = new ArrayList<AComment>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERID, USERPWD);
            pstmt = conn.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AComment aComment = new AComment();
                aComment.setActivityNo(rs.getInt("acommentno"));
                aComment.setActivityNo(rs.getInt("activityno"));
                aComment.setMemberNo(rs.getInt("memberno"));
                aComment.setACommentCont(rs.getString("acommentcont"));
                aComment.setARate(rs.getInt("arate"));
                aComment.setACommentDate(rs.getDate("acommentdate"));
                aComment.setACommentStatus(rs.getInt("acommentstatus"));
                aComment.setALike(rs.getInt("alike"));
                list.add(aComment);
            }

            return list;

        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
            return null;
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
//        新增評論測試: 成功
//        AComment comment = new AComment();
//        comment.setActivityNo(2);
//        comment.setMemberNo(85342);
//        comment.setaCommentCont("演唱會場地狹小擁擠，觀眾素質也很差，演唱會不愉快");
//        comment.setaRate(1);
//        ACommentDaoImpl dao = new ACommentDaoImpl();
//        dao.insert(comment);

//        取得所有評論測試: 成功
//        ACommentDaoImpl dao = new ACommentDaoImpl();
//        List<AComment> list = dao.getAll();
//        for (AComment aComment : list) {
//            System.out.println(aComment.getaCommentNo());
//        }

//        取得單一評論測試: 成功
//        ACommentDaoImpl dao = new ACommentDaoImpl();
//        AComment aComment = aComment = dao.getById(2);
//        System.out.println(aComment.getaCommentCont());

//        更新評論測試: 成功
//        ACommentDaoImpl dao = new ACommentDaoImpl();
//        AComment aComment = dao.getById(7);
//        aComment.setaCommentStatus(5);
//        dao.update(aComment);

//        移除評論測試:
//        ACommentDaoImpl dao = new ACommentDaoImpl();
//        dao.deleteById(12);
    }
}
