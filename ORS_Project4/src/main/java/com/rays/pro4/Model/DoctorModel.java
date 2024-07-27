package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.DoctorBean;
import com.rays.pro4.Bean.DoctorBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class DoctorModel {

	public int nextPK() throws Exception {

		String sql = "SELECT MAX(ID) FROM ST_DOCTOR";
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

	public long add(DoctorBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_DOCTOR VALUES(?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getName());
		ps.setDate(3, new Date(bean.getDob().getTime()));
		ps.setString(4, bean.getMobileNo());
		ps.setString(5, bean.getExpertise());

		ps.executeUpdate();

		return pk;

	}

	public void update(DoctorBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_DOCTOR SET NAME=?, DOB=?, MOBILE_NO=?, EXPERTISE=? WHERE ID=?");

		ps.setString(1, bean.getName());
		ps.setDate(2, new Date(bean.getDob().getTime()));
		ps.setString(3, bean.getMobileNo());
		ps.setString(4, bean.getExpertise());

		ps.setLong(5, bean.getId());

		ps.executeUpdate();

	}

	public void delete(DoctorBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_DOCTOR WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public DoctorBean findByPK(long pk) throws Exception {

		String sql = "SELECT * FROM ST_DOCTOR WHERE ID=?";
		DoctorBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new DoctorBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setMobileNo(rs.getString(4));
			bean.setExpertise(rs.getString(5));

		}
		return bean;
	}

	public List<DoctorBean> search(DoctorBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_DOCTOR WHERE 1=1");
		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND ID =" + bean.getId());
			}

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}

			
			  if (bean.getDob() != null && bean.getDob().getTime() > 0) { 
				  Date d = new java.sql.Date(bean.getDob().getTime()); 
				  sql.append(" AND DOB like '" + d+ "%'" ); }
			 
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				Date d = new Date(bean.getDob().getDate());
				sql.append(" AND DOB = " + DataUtility.getDateString(d));
			}
			if (bean.getMobileNo()!= null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO =  " + bean.getMobileNo());
			}

			if (bean.getExpertise() != null && bean.getExpertise().length() > 0) {
				sql.append(" AND EXPERTISE like '" + bean.getExpertise() + "%'");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List<DoctorBean> list = new ArrayList<DoctorBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new DoctorBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setMobileNo(rs.getString(4));
				bean.setExpertise(rs.getString(5));

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

	public List<DoctorBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<DoctorBean> list = new ArrayList<DoctorBean>();
		StringBuffer sql = new StringBuffer("select * from ST_DOCTOR");

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
				DoctorBean bean = new DoctorBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setMobileNo(rs.getString(4));
				bean.setExpertise(rs.getString(5));

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
