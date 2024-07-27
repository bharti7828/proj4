package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.FieldBean;
import com.rays.pro4.Bean.FieldBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class FieldModel {

	public int nextPK() throws Exception {

		String sql = "SELECT MAX(ID) FROM ST_FIELD";
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

	public long add(FieldBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_FIELD VALUES(?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getLabel());
		ps.setString(3, bean.getType());
		ps.setString(4, bean.getActive());
		ps.setString(5, bean.getDescription());

		ps.executeUpdate();

		return pk;

	}

	public void update(FieldBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_FIELD SET LABEL=?, TYPE=?, ACTIVE=?,DESCRIPTION=? WHERE ID=?");

		ps.setString(1, bean.getLabel());
		ps.setString(2, bean.getType());
		ps.setString(3, bean.getActive());
		ps.setString(4, bean.getDescription());

		ps.setLong(5, bean.getId());

		ps.executeUpdate();

	}

	public void delete(FieldBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_FIELD WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public FieldBean findByPK(long pk) throws Exception {

		String sql = "SELECT * FROM ST_FIELD WHERE ID=?";
		FieldBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new FieldBean();
			bean.setId(rs.getLong(1));
			bean.setLabel(rs.getString(2));
			bean.setType(rs.getString(3));
			bean.setActive(rs.getString(4));
			bean.setDescription(rs.getString(5));

		}
		return bean;
	}

	public List<FieldBean> search(FieldBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FIELD WHERE 1=1");
		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND ID =" + bean.getId());
			}

			if (bean.getLabel() != null && bean.getLabel().length() > 0) {
				sql.append(" AND LABEL like '" + bean.getLabel() + "%'");
			}

			if (bean.getType() != null && bean.getType().length() > 0) {
				sql.append(" AND  TYPE like '" + bean.getType() + "%'");
			}



			if (bean.getActive() != null && bean.getActive().length() > 0) {
				sql.append(" AND  ACTIVE like '" + bean.getActive() + "%'");
			}
			

			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + bean.getDescription()+ "%'");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List<FieldBean> list = new ArrayList<FieldBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new FieldBean();
				bean.setId(rs.getLong(1));
				bean.setLabel(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setActive(rs.getString(4));
				bean.setDescription(rs.getString(5));

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

	public List<FieldBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<FieldBean> list = new ArrayList<FieldBean>();
		StringBuffer sql = new StringBuffer("select * from ST_FIELD");

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
				FieldBean bean = new FieldBean();
				bean.setId(rs.getLong(1));
				bean.setLabel(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setActive(rs.getString(4));
				bean.setDescription(rs.getString(5));

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
