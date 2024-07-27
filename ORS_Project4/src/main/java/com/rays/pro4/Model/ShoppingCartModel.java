package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ShoppingCartBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class ShoppingCartModel {

	public int nextPK() throws Exception {

		String sql = "SELECT MAX(ID) FROM ST_SHOPPINGCART";
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

	public long add(ShoppingCartBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_SHOPPINGCART VALUES(?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getName());
		ps.setString(3, bean.getProduct());
		ps.setDate(4, new Date(bean.getDate().getTime()));
		
		ps.setLong(5, bean.getQuantity());

		ps.executeUpdate();

		return pk;

	}

	public void update(ShoppingCartBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_SHOPPINGCART SET NAME=?, PRODUCT=?, DATE=?, QUANTITY=? WHERE ID=?");

		ps.setString(1, bean.getName());
		ps.setString(2, bean.getProduct());
		ps.setDate(3, new Date(bean.getDate().getTime()));
		ps.setLong(4, bean.getQuantity());

		ps.setLong(5, bean.getId());

		ps.executeUpdate();

	}

	public void delete(ShoppingCartBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_SHOPPINGCART WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public ShoppingCartBean findByPK(long pk) throws Exception {

		String sql = ("SELECT * FROM ST_SHOPPINGCART WHERE ID=?");
		ShoppingCartBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new ShoppingCartBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setProduct(rs.getString(3));
			bean.setDate(rs.getDate(4));
			bean.setQuantity(rs.getLong(5));	
		}
		return bean;
	}

	public List<ShoppingCartBean> search(ShoppingCartBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SHOPPINGCART WHERE 1=1");
		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND ID =" + bean.getId());
			}

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getProduct() != null && bean.getProduct().length() > 0) {
				sql.append(" AND PRODUCT like '" + bean.getProduct() + "%'");
			}


			if (bean.getDate() != null && bean.getDate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getDate().getTime());
				sql.append(" AND DATE like '" +d+ "%'");
			}

			
			if (bean.getQuantity()  > 0) {
				sql.append(" AND QUANTITY = " + bean.getQuantity());
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List<ShoppingCartBean> list = new ArrayList<ShoppingCartBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ShoppingCartBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setProduct(rs.getString(3));
				bean.setDate(rs.getDate(4));
				bean.setQuantity(rs.getLong(5));				
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

	public List<ShoppingCartBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<ShoppingCartBean> list = new ArrayList<ShoppingCartBean>();
		StringBuffer sql = new StringBuffer("select * from ST_SHOPPINGCART");

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
				ShoppingCartBean bean = new ShoppingCartBean();
				bean = new ShoppingCartBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setProduct(rs.getString(3));

				bean.setDate(rs.getDate(4));
				
				bean.setQuantity(rs.getLong(5));
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
