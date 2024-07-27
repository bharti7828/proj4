package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.WishBean;
import com.rays.pro4.Bean.WishBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class WishModel {

	public int nextPK() throws Exception {

		String sql = "SELECT MAX(ID) FROM ST_Wish";
		Connection conn = null;
		int pk = 0;

		conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			pk = rs.getInt(1);

		}
		rs.close();
		return pk + 1;

	}

	public long add(WishBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_Wish VALUES(?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getProduct());
		ps.setDate(3, new Date(bean.getDate().getTime()));
		ps.setString(4, bean.getUserName());
		ps.setString(5, bean.getRemark());

		ps.executeUpdate();

		return pk;

	}

	public void update(WishBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_Wish SET PRODUCT=?, DATE=?, USER_NAME=?, REMARK=? WHERE ID=?");

		ps.setString(1, bean.getProduct());
		ps.setDate(2, new Date(bean.getDate().getTime()));
		ps.setString(3, bean.getUserName());
		ps.setString(4, bean.getRemark());

		ps.setLong(5, bean.getId());

		ps.executeUpdate();

	}

	public void delete(WishBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_Wish WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public WishBean findByPK(long pk) throws Exception {

		String sql = "SELECT * FROM ST_Wish WHERE ID=?";
		WishBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new WishBean();
			bean.setId(rs.getLong(1));
			bean.setProduct(rs.getString(2));
			bean.setDate(rs.getDate(3));
			bean.setUserName(rs.getString(4));
			bean.setRemark(rs.getString(5));

		}
		return bean;
	}

	public List<WishBean> search(WishBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_Wish WHERE 1=1");
		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND ID =" + bean.getId());
			}

			if (bean.getProduct() != null && bean.getProduct().length() > 0) {
				sql.append(" AND PRODUCT like '" + bean.getProduct()+ "%'");
			}

			if (bean.getDate() != null && bean.getDate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getDate().getTime());
				sql.append(" AND DATE like '" + d + "%'");
			}

			/*
			 * if (bean.getDob() != null && bean.getDob().getDate() > 0) { Date d = new
			 * Date(bean.getDob().getDate()); sql.append(" AND DOB = " +
			 * DataUtility.getDateString(d)); }
			 */if (bean.getUserName()!= null && bean.getUserName().length() > 0) {
					sql.append(" AND USER_NAME like '" + bean.getUserName() + "%'");
				}

			if (bean.getRemark() != null && bean.getRemark().length() > 0) {
				sql.append(" AND REMARK like '" + bean.getRemark()+ "%'");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List<WishBean> list = new ArrayList<WishBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new WishBean();
				bean.setId(rs.getLong(1));
				bean.setProduct(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setUserName(rs.getString(4));
				bean.setRemark(rs.getString(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception: Exception in Search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	public List<WishBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<WishBean> list = new ArrayList<WishBean>();
		StringBuffer sql = new StringBuffer("select * from ST_Wish");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println(sql);
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				WishBean bean = new WishBean();
				bean.setId(rs.getLong(1));
				bean.setProduct(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setUserName(rs.getString(4));
				bean.setRemark(rs.getString(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}
