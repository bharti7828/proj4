package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.CustomerBean;
import com.rays.pro4.Bean.CustomerBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class CustomerModel {

	public int nextPK() throws Exception {

		String sql = ("SELECT MAX(ID) FROM ST_CUSTOMER");
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

	public long add(CustomerBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		int pk = 0;
		pk = nextPK();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_CUSTOMER VALUES(?,?,?,?,?,?,?,?)");

		ps.setLong(1, pk);
		ps.setString(2, bean.getFirstName());
		ps.setString(3, bean.getLastName());
		ps.setDate(4, new Date(bean.getDob().getTime()));
		ps.setString(5, bean.getGender());
		ps.setString(6, bean.getCity());
		ps.setString(7, bean.getCompany());
		ps.setLong(8, bean.getPhoneNo());
		
		

		ps.executeUpdate();

		return pk;

	}

	public void update(CustomerBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("UPDATE ST_CUSTOMER SET FIRST_NAME=?,LAST_NAME=?, DOB=?, GENDER=?, CITY=? , COMPANY=?,PHONENO=? WHERE ID=?");

		ps.setString(1, bean.getFirstName());
		ps.setString(2, bean.getLastName());
		ps.setDate(3, new Date(bean.getDob().getTime()));
		ps.setString(4, bean.getGender());
		ps.setString(5, bean.getCity());
		ps.setString(6, bean.getCompany());
		ps.setLong(7, bean.getPhoneNo());

		
				ps.setLong(8, bean.getId());

		ps.executeUpdate();

	}

	public void delete(CustomerBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_CUSTOMER WHERE ID=?");

		ps.setLong(1, bean.getId());
		ps.executeUpdate();
	}

	public CustomerBean findByPK(long pk) throws Exception {

		String sql = "SELECT * FROM ST_CUSTOMER WHERE ID=?";
		CustomerBean bean = null;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new CustomerBean();
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setGender(rs.getString(5));
			bean.setCity(rs.getString(6));
			bean.setCompany(rs.getString(7));
			bean.setPhoneNo(rs.getLong(8));


		}
		return bean;
	}

	public List<CustomerBean> search(CustomerBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_CUSTOMER WHERE 1=1");
		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND ID =" + bean.getId());
			}

			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName()+ "%'");
			}


			
//			  if (bean.getDob() != null && bean.getDob().getTime() > 0) { 
//				  Date d = new java.sql.Date(bean.getDob().getTime()); 
//				  sql.append(" AND DOB like '" + d+ "%'" ); }
//			 
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				Date d = new Date(bean.getDob().getDate());
				sql.append(" AND DOB = " + DataUtility.getDateString(d));
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + bean.getGender() + "%'");
			}

			

			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" AND CITY like '" + bean.getCity() + "%'");
			}
			
			if (bean.getCompany() != null && bean.getCompany().length() > 0) {
				sql.append(" AND COMPANY like '" + bean.getCompany() + "%'");
			}
			if (bean.getPhoneNo() > 0) {
				sql.append(" AND PHONENO =  " + bean.getPhoneNo());
			}


		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql ====> " + sql.toString());
		List<CustomerBean> list = new ArrayList<CustomerBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setCity(rs.getString(6));
				bean.setCompany(rs.getString(7));
				bean.setPhoneNo(rs.getLong(8));

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

	public List<CustomerBean> list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList<CustomerBean> list = new ArrayList<CustomerBean>();
		StringBuffer sql = new StringBuffer("select * from ST_CUSTOMER");

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
				CustomerBean bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setGender(rs.getString(5));
				bean.setCity(rs.getString(6));
				bean.setCompany(rs.getString(7));
				bean.setPhoneNo(rs.getLong(8));


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
