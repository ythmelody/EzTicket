package com.ezticket.web.activity.repository.impl;

import com.ezticket.web.activity.pojo.CollectVO;
import com.ezticket.web.activity.repository.CollectDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectDAOImpl implements CollectDAO {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/db01?serverTimezone=Asia/Taipei";
    String userid = "root";
    String passwd = "root";

    private static final String INSERT_STMT =
            "INSERT INTO collect (memberno, tdetailsno, tstatus, qrcode) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_STMT =
            "SELECT m.mname, a.activityno, a.aname, i.aimg, ss.sessionstime, st.realx, st.realy, c.collectno, c.tdetailsno, c.tstatus "
                    + "FROM collect c JOIN member m ON c.memberno = m.memberno "
                    + "JOIN tdetails d ON c.tdetailsno = d.tdetailsno "
                    + "JOIN seats st ON d.seatno = st.seatno "
                    + "JOIN session ss ON d.sessionno = ss.sessionno "
                    + "JOIN activity a ON ss.activityno = a.activityno "
                    + "JOIN aimgt i ON a.activityno = i.activityno "
                    + "WHERE m.memberno = 85341 AND i.aimgmain = 1 "
                    + "ORDER BY collectno";
    private static final String GET_ONE_STMT =
            "SELECT collectno, memberno, tdetailsno, tstatus, qrcode FROM collect WHERE collectno = ?";
    private static final String DELETE =
            "DELETE FROM collect WHERE collectno = ?";
    private static final String UPDATE =
            "UPDATE collect SET memberno = ?, tdetailsno = ?, tstatus = ?, qrcode = ? WHERE collectno = ?";

    @Override
    public void insert(CollectVO collectVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setInt(1, collectVO.getMemberno());
            pstmt.setInt(2, collectVO.gettDetailsno());
            pstmt.setInt(3, collectVO.gettStatus());
            pstmt.setBytes(4, collectVO.getQrcode());

            pstmt.executeUpdate();

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    @Override
    public void update(CollectVO collectVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setInt(1, collectVO.getMemberno());
            pstmt.setInt(2, collectVO.gettDetailsno());
            pstmt.setInt(3, collectVO.gettStatus());
            pstmt.setBytes(4, collectVO.getQrcode());
            pstmt.setInt(5, collectVO.getCollectno());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    @Override
    public void delete(Integer collectno) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE);

            pstmt.setInt(1, collectno);

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    @Override
    public CollectVO findByPK(Integer collectno) {

        CollectVO collectVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, collectno);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                collectVO = new CollectVO();
                collectVO.setCollectno(rs.getInt("collectno"));
                collectVO.setMemberno(rs.getInt("memberno"));
                collectVO.settDetailsno(rs.getInt("tdetailsno"));
                collectVO.settStatus(rs.getInt("tstatus"));
                collectVO.setQrcode(rs.getBytes("qrcode"));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return collectVO;
    }

    @Override
    public List<CollectVO> getAll() {
        List<CollectVO> list = new ArrayList<CollectVO>();
        CollectVO collectVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                collectVO = new CollectVO();
                collectVO.setCollectno(rs.getInt("collectno"));
                collectVO.setMemberno(rs.getInt("memberno"));
                collectVO.settDetailsno(rs.getInt("tdetailsno"));
                collectVO.settStatus(rs.getInt("tstatus"));
                collectVO.setQrcode(rs.getBytes("qrcode"));
                list.add(collectVO); // Store the row in the list
            }

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return list;
    }
}
