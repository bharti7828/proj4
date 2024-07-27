package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.JobBean;
import com.rays.pro4.Bean.JobBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class JobModel {

	public int nextPK() throws Exception {

		String sql = "SELECT MAX(ID) FROM ST_JOB";
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

	public long add(JobBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_JOB VALUES(?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getTitle());
		ps.setDate(3, new Date(bean.getDateOfOpening().getTime()));
		ps.setString(4, bean.getExperience());
		ps.setString(5, bean.getStatus());

		ps.executeUpdate();

		return pk;

	}

	public void update(JobBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_JOB SET TITLE=?, DATE_OF_OPENING=?, EXPERIENCE=?, STATUS=? WHERE ID=?");

		ps.setString(1, bean.getTitle());
		ps.setDate(2, new Date(bean.getDateOfOpening().getTime()));
		ps.setString(3, bean.getExperience());
		ps.setString(4, bean.getStatus());
		ps.setLong(5, bean.getId());

		ps.executeUpdate();

	}

	public void delete(JobBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_JOB WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public JobBean findByPK(long pk) throws Exception {

		String sql = "SELECT * FROM ST_JOB WHERE ID=?";
		JobBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new JobBean();
			bean.setId(rs.getLong(1));
			bean.setTitle(rs.getString(2));
			bean.setDateOfOpening(rs.getDate(3));
			bean.setExperience(rs.getString(4));
			bean.setStatus(rs.getString(5));

		}
		return bean;
	}

	public List<JobBean> search(JobBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_JOB WHERE 1=1");
		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND ID =" + bean.getId());
			}

			if (bean.getTitle() != null && bean.getTitle().length() > 0) {
				sql.append(" AND TITLE like '" + bean.getTitle() + "%'");
			}

			if (bean.getDateOfOpening() != null && bean.getDateOfOpening().getTime() > 0) {
				Date d = new java.sql.Date(bean.getDateOfOpening().getTime());
				sql.append(" AND DATE_OF_OPENING like '" + d + "%'");
			}

			if (bean.getExperience() != null && bean.getExperience().length() > 0) {
				sql.append(" AND EXPERIENCE like '" + bean.getExperience() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND STATUS like '" + bean.getStatus() + "%'");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List<JobBean> list = new ArrayList<JobBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new JobBean();
				bean = new JobBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDateOfOpening(rs.getDate(3));
				bean.setExperience(rs.getString(4));
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

	public List<JobBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<JobBean> list = new ArrayList<JobBean>();
		StringBuffer sql = new StringBuffer("select * from ST_JOB");

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
				JobBean bean = new JobBean();
				bean = new JobBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDateOfOpening(rs.getDate(3));
				bean.setExperience(rs.getString(4));
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
