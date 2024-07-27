package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ProjectBean;
import com.rays.pro4.Bean.ProjectBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class ProjectModel {

	public int nextPK() throws Exception {

		String sql = ("SELECT MAX(ID) FROM ST_Project");
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

	public long add(ProjectBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_Project VALUES(?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getName());
		ps.setString(3, bean.getCategory());
		ps.setDate(4, new Date(bean.getOpenDate().getTime()));
		ps.setDouble(5, bean.getVersion());


		ps.executeUpdate();

		return pk;

	}

	public void update(ProjectBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_Project SET NAME=?,CATEGORY=?, OPEN_DATE=?,VERSION=? WHERE ID=?");

		ps.setString(1, bean.getName());
		ps.setString(2, bean.getCategory());
		ps.setDate(3, new Date(bean.getOpenDate().getTime()));
		ps.setDouble(4, bean.getVersion());
		ps.setLong(5, bean.getId());


		ps.executeUpdate();

	}

	public void delete(ProjectBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_Project WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public ProjectBean findByPK(long pk) throws Exception {

		String sql = ("SELECT * FROM ST_Project WHERE ID=?");
		ProjectBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new ProjectBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setCategory(rs.getString(3));
			bean.setOpenDate(rs.getDate(4));
			bean.setVersion(rs.getDouble(5));

			 
		}
		return bean;
	}

	public List search(ProjectBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_Project WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND ID =" + bean.getId());

				}
			
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getOpenDate()!= null && bean.getOpenDate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getOpenDate().getTime());
				sql.append(" AND OPEN_DATE like '"+d+"%'");
			}
			if (bean.getCategory() != null && bean.getCategory().length() > 0) {
				sql.append(" AND CATEGORY like '" + bean.getCategory() + "%'");
			}
			/*
			 * if (bean.getOpenDate() != null && bean.getOpenDate().getDate() > 0) { Date d
			 * = new Date(bean.getOpenDate().getDate()); sql.append(" AND OPEN_DATE = " +
			 * DataUtility.getDateString(d)); }
			 */
			if (bean.getVersion() > 0) {
				sql.append(" AND VERSION =  " + bean.getVersion());
				
			}
			
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ProjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getString(3));
				bean.setOpenDate(rs.getDate(4));
				bean.setVersion(rs.getDouble(5));

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
		StringBuffer sql = new StringBuffer("select * from st_Project");

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
				ProjectBean bean = new ProjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getString(3));
				bean.setOpenDate(rs.getDate(4));
				bean.setVersion(rs.getDouble(5));
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
