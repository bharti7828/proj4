package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Util.JDBCDataSource;


public class ProductModel {

	public int nextPK() throws Exception {

		
		Connection conn = null;
		int pk = 0;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select max(id) from ST_PRODUCT");
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			pk = rs.getInt(1);
		}
		rs.close();
		return pk + 1;
	}

	public long add(ProductBean bean) throws Exception {

		String sql = "INSERT INTO ST_PRODUCT VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		conn = JDBCDataSource.getConnection();
		pk = nextPK();

		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);
		pstmt.setString(2, bean.getName());
		pstmt.setDate(3, new Date(bean.getDob().getTime()));
		pstmt.setString(4, bean.getDiscription());
		pstmt.setString(5, bean.getPrice());

		int a = pstmt.executeUpdate();
		conn.commit();
		pstmt.close();

		return pk;

	}

	public void delete(ProductBean bean) throws ApplicationException {
		String sql = "DELETE FROM ST_PRODUCT WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public ProductBean findByPK(long pk) throws Exception {
		ProductBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ST_PRODUCT WHERE ID=?");
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			bean = new ProductBean();

			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));

			bean.setDob(rs.getDate(3));
			bean.setDiscription(rs.getString(4));
			bean.setPrice(rs.getString(5));

		}

		return bean;

	}

	public void update(ProductBean bean) throws Exception {
		String sql = "UPDATE ST_PRODUCT SET NAME =?, DOB =?, DISCRIPTION =?, PRICE =?  WHERE ID =?";
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, bean.getName());
		pstmt.setDate(2, new Date(bean.getDob().getTime()));
		pstmt.setString(3, bean.getDiscription());
		pstmt.setString(4, bean.getPrice());

		pstmt.setLong(5, bean.getId());

		pstmt.executeUpdate();
		conn.commit();
		pstmt.close();

		conn.rollback();

	}
	

	public List search(ProductBean bean, int pageNo, int pageSize) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_PRODUCT WHERE 1=1");
		
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND name like '" + bean.getName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new java.sql.Date(bean.getDob().getTime());
				sql.append(" AND DOB like '"+d+"%'");
			}
			if (bean.getDiscription() != null && bean.getDiscription().length() > 0) {
				sql.append(" AND DISCRIPTION like '" + bean.getDiscription() + "%'");
			}
			if (bean.getPrice() != null && bean.getPrice().length() > 0) {
				sql.append(" AND PRICE like '" + bean.getPrice() + "%'");
			}
			if (bean.getId() > 0) {
				sql.append(" AND ID  = " + bean.getId());
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + " , " + pageSize);
		}
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));

				bean.setDob(rs.getDate(3));
				bean.setDiscription(rs.getString(4));
				bean.setPrice(rs.getString(5));

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

	public List list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_product");

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
				ProductBean bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setDiscription(rs.getString(4));
				bean.setPrice(rs.getString(5));

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
