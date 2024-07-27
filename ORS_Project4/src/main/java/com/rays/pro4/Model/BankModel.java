package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class BankModel {

	public int nextPK() throws Exception {

		String sql = "SELECT MAX(ID) FROM ST_BANK";
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

	public long add(BankBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_BANK VALUES(?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getName());
		ps.setDate(3, new Date(bean.getDob().getTime()));
		ps.setLong(4, bean.getAccountNo());
		ps.setString(5, bean.getBankName());

		ps.executeUpdate();

		return pk;

	}

	public void update(BankBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_BANK SET NAME=?, DOB=?, ACCOUNTNO=?, BANKNAME=? WHERE ID=?");

		ps.setString(1, bean.getName());
		ps.setDate(2, new Date(bean.getDob().getTime()));
		ps.setLong(3, bean.getAccountNo());
		ps.setString(4, bean.getBankName());
		ps.setLong(5, bean.getId());

		ps.executeUpdate();

	}

	public void delete(BankBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_BANK WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public BankBean findByPK(long pk) throws Exception {

		String sql = "SELECT * FROM ST_BANK WHERE ID=?";
		BankBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new BankBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setAccountNo(rs.getLong(4));
			bean.setBankName(rs.getString(5));

		}
		return bean;
	}

	public List<BankBean> search(BankBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_BANK WHERE 1=1");
		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND ID =" + bean.getId());
			}

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}

			
			
			  if (bean.getDob() != null && bean.getDob().getTime() > 0) { 
				  Date d = new java.sql.Date(bean.getDob().getTime());
			  sql.append(" AND DOB like '" + d+"%'" ); }
			 
			 
			/*
			 * if (bean.getDob() != null && bean.getDob().getDate() > 0) { Date d = new
			 * Date(bean.getDob().getDate()); sql.append(" AND DOB = " +
			 * DataUtility.getDateString(d)); }
			 */
			if (bean.getAccountNo() > 0) {
				sql.append(" AND ACCOUNTNO =  " + bean.getAccountNo());
			}

			if (bean.getBankName() != null && bean.getBankName().length() > 0) {
				sql.append(" AND BANKNAME like '" + bean.getBankName() + "%'");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List<BankBean> list = new ArrayList<BankBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setAccountNo(rs.getLong(4));
				bean.setBankName(rs.getString(5));
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

	public List<BankBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<BankBean> list = new ArrayList<BankBean>();
		StringBuffer sql = new StringBuffer("select * from st_bank");

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
				BankBean bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setAccountNo(rs.getLong(4));
				bean.setBankName(rs.getString(5));

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
