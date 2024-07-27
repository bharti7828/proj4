package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.OwnerBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class OwnerModel {

	public int nextPK() throws Exception {

		String sql = ("SELECT MAX(ID) FROM ST_OWNER");
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

	public long add(OwnerBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_OWNER VALUES(?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getName());
		ps.setDate(3, new Date(bean.getDob().getTime()));
		ps.setString(4, bean.getVehicleID());
		ps.setLong(5, bean.getInsuranceAmout());

		ps.executeUpdate();

		return pk;

	}

	public void update(OwnerBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_OWNER SET NAME=?, DOB=?, VEHICLEID=?, INSURANCEAMOUT=? WHERE ID=?");

		ps.setString(1, bean.getName());
		ps.setDate(2, new Date(bean.getDob().getTime()));
		ps.setString(3, bean.getVehicleID());
		ps.setLong(4, bean.getInsuranceAmout());

		ps.setLong(5, bean.getId());

		ps.executeUpdate();

	}

	public void delete(OwnerBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_OWNER WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public OwnerBean findByPK(long pk) throws Exception {

		String sql = "SELECT * FROM ST_OWNER WHERE ID=?";
		OwnerBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new OwnerBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setVehicleID(rs.getString(4));
			bean.setInsuranceAmout(rs.getLong(5));

		}
		return bean;
	}

	public List<OwnerBean> search(OwnerBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_OWNER WHERE 1=1");
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
			 
			/*
			 * if (bean.getDob() != null && bean.getDob().getDate() > 0) { Date d = new
			 * Date(bean.getDob().getDate()); sql.append(" AND DOB = " +
			 * DataUtility.getDateString(d)); }
			 */
			if (bean.getVehicleID()!= null && bean.getVehicleID().length() > 0) {
				sql.append(" AND VEHICLEID like  '" + bean.getVehicleID() + "%'");
			}

			if (bean.getInsuranceAmout() > 0) {
				sql.append(" AND INSURANCEAMOUT =" + bean.getInsuranceAmout());
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List<OwnerBean> list = new ArrayList<OwnerBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OwnerBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setVehicleID(rs.getString(4));
				bean.setInsuranceAmout(rs.getLong(5));

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

	public List<OwnerBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<OwnerBean> list = new ArrayList<OwnerBean>();
		StringBuffer sql = new StringBuffer("select * from ST_OWNER");

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
				OwnerBean bean = new OwnerBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setVehicleID(rs.getString(4));
				bean.setInsuranceAmout(rs.getLong(5));
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
