package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.SalaryBean;
import com.rays.pro4.Bean.SalaryBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class SalaryModel {

	public int nextPK() throws Exception {

		String sql = "SELECT MAX(ID) FROM ST_SALARY";
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

	public long add(SalaryBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_SALARY VALUES(?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getEmployee());
		ps.setInt(3, bean.getAmount());
		ps.setDate(4, new Date(bean.getApplieddate().getTime()));

		ps.setString(5, bean.getStatus());

		ps.executeUpdate();

		return pk;

	}

	public void update(SalaryBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_SALARY SET EMPLOYEE=?, AMOUNT=?, APPLIEDDATE=?, STATUS=? WHERE ID=?");

		ps.setString(1, bean.getEmployee());
		ps.setInt(2, bean.getAmount());
		ps.setDate(3, new Date(bean.getApplieddate().getTime()));

		ps.setString(4, bean.getStatus());

		ps.setLong(5, bean.getId());

		ps.executeUpdate();

	}

	public void delete(SalaryBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_SALARY WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public SalaryBean findByPK(long pk) throws Exception {

		String sql = "SELECT * FROM ST_SALARY WHERE ID=?";
		SalaryBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new SalaryBean();
			bean.setId(rs.getLong(1));
			bean.setEmployee(rs.getString(2));
			bean.setAmount(rs.getInt(3));
			bean.setApplieddate(rs.getDate(4));

			bean.setStatus(rs.getString(5));

		}
		return bean;
	}

	public List<SalaryBean> search(SalaryBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SALARY WHERE 1=1");
		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND ID =" + bean.getId());
			}

			if (bean.getEmployee() != null && bean.getEmployee().length() > 0) {
				sql.append(" AND EMPLOYEE like '" + bean.getEmployee() + "%'");
			}
			if (bean.getAmount() > 0) {
				sql.append(" AND AMOUNT =  " + bean.getAmount());
			}

			if (bean.getApplieddate() != null && bean.getApplieddate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getApplieddate().getTime());
				sql.append(" AND APPLIEDDATE like '" + d + "%'");
			}

			/*
			 * if (bean.getDob() != null && bean.getDob().getDate() > 0) { Date d = new
			 * Date(bean.getDob().getDate()); sql.append(" AND DOB = " +
			 * DataUtility.getDateString(d)); }
			 */

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND STATUS like '" + bean.getStatus() + "%'");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List<SalaryBean> list = new ArrayList<SalaryBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SalaryBean();
				bean.setId(rs.getLong(1));
				bean.setEmployee(rs.getString(2));
				bean.setAmount(rs.getInt(3));
				bean.setApplieddate(rs.getDate(4));

				bean.setStatus(rs.getString(5));

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

	public List<SalaryBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<SalaryBean> list = new ArrayList<SalaryBean>();
		StringBuffer sql = new StringBuffer("select * from ST_SALARY");

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
				SalaryBean bean = new SalaryBean();
				bean = new SalaryBean();
				bean.setId(rs.getLong(1));
				bean.setEmployee(rs.getString(2));
				bean.setAmount(rs.getInt(3));
				bean.setApplieddate(rs.getDate(4));

				bean.setStatus(rs.getString(5));

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
