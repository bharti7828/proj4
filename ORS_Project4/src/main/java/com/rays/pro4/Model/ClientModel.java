package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.JDBCDataSource;

public class ClientModel {
	
	public int nextPK()throws Exception{
		
				
		String sql = ("SELECT MAX(ID) FROM ST_Client");
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
	
	public long add(ClientBean bean)throws Exception{
		
		Connection conn= JDBCDataSource.getConnection();
		int pk=0;
		pk=nextPK();
		PreparedStatement ps= conn.prepareStatement("INSERT INTO ST_Client VALUES(?,?,?,?,?)");
		
		ps.setLong(1,pk);
		ps.setString(2,bean.getName());
		ps.setString(3, bean.getAddress()); 
		ps.setLong(4,bean.getPhoneNo());
		ps.setString(5,bean.getPriority());

		
		
		
		
		ps.executeUpdate();

		return pk;
		
		
	}
	
	public void update(ClientBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("UPDATE ST_Client SET NAME = ?, ADDRESS = ?, PHONE_NO = ?, PRIORITY = ? WHERE ID=?");
		
		ps.setString(1,bean.getName());
		ps.setString(2, bean.getAddress()); 
		ps.setLong(3,bean.getPhoneNo());
		ps.setString(4,bean.getPriority());
		ps.setLong(5, bean.getId());

		ps.executeUpdate();
		
	}
	
	public void delete(ClientBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps= conn.prepareStatement("DELETE FROM ST_Client WHERE ID=?");
		
		ps.setLong(1,bean.getId());
		ps.executeUpdate();
	}
	
	
	public ClientBean findByPK(long pk) throws Exception {
	
	String sql = ("SELECT * FROM ST_Client WHERE ID=?");
	ClientBean bean = null;
	Connection conn = null;
	
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new ClientBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setPhoneNo(rs.getLong(4));
			bean.setPriority(rs.getString(5));
			
						
			
			
		/* rs.close();*/ 
		}
      return bean;
}
	public List search(ClientBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_Client WHERE 1=1");
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getId()  > 0) {
				sql.append(" AND ID = '" + bean.getId());
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
			}		   
			
			if (bean.getPhoneNo()  > 0) {
				sql.append(" AND PHONE_NO like '" + bean.getPhoneNo() + "%'");
			}
			
			if (bean.getPriority()!= null && bean.getPriority().length() > 0) {
				sql.append(" AND PRIORITY like '" + bean.getPriority() + "%'");
			}
		}
		   
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ClientBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setPhoneNo(rs.getLong(4));
				bean.setPriority(rs.getString(5));
				
				
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
		StringBuffer sql = new StringBuffer("select * from ST_Client ");

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
				ClientBean bean = new ClientBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setPhoneNo(rs.getLong(4));
				bean.setPriority(rs.getString(5));
				
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
