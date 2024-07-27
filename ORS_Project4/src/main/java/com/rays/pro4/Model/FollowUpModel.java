package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.JDBCDataSource;

public class FollowUpModel {
	
	public int nextPK()throws Exception{
		
				
		String sql = ("SELECT MAX(ID) FROM ST_FOLLOWUP");
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
	
	public long add(FollowUpBean bean)throws Exception{
		
		Connection conn= JDBCDataSource.getConnection();
		int pk=0;
		pk=nextPK();
		PreparedStatement ps= conn.prepareStatement("INSERT INTO ST_FOLLOWUP VALUES(?,?,?,?,?)");
		
		ps.setLong(1,pk);
		ps.setString(2,bean.getPatient());
		ps.setString(3,bean.getDoctor());
		ps.setDate(4, new Date(bean.getVisitDate().getTime()));
		ps.setInt(5,bean.getFees());

		
		
		
		
		ps.executeUpdate();

		return pk;
		
		
	}
	
	public void update(FollowUpBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("UPDATE ST_FOLLOWUP SET PATIENT = ?,DOCTOR = ?, VISITDATE = ?, FEES = ? WHERE ID=?");
		
		ps.setString(1,bean.getPatient());
		ps.setString(2,bean.getDoctor());
		ps.setDate(3, new Date(bean.getVisitDate().getTime()));
		ps.setInt(4,bean.getFees());
		ps.setLong(5, bean.getId());

		ps.executeUpdate();
		
	}
	
	public void delete(FollowUpBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps= conn.prepareStatement("DELETE FROM ST_FOLLOWUP WHERE ID=?");
		
		ps.setLong(1,bean.getId());
		ps.executeUpdate();
	}
	
	
	public FollowUpBean findByPK(long pk) throws Exception {
	
	String sql = ("SELECT * FROM ST_FOLLOWUP WHERE ID=?");
	FollowUpBean bean = null;
	Connection conn = null;
	
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new FollowUpBean();
			bean.setId(rs.getLong(1));
			bean.setPatient(rs.getString(2));
			bean.setDoctor(rs.getString(3));
			bean.setVisitDate(rs.getDate(4));
			bean.setFees(rs.getInt(5));
			
			
			
		/* rs.close();*/ 
		}
      return bean;
}
	public List search(FollowUpBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FOLLOWUP WHERE 1=1");
		if (bean != null) {
			if (bean.getId()  > 0) {
				sql.append(" AND ID = '" + bean.getId());
			}

			if (bean.getPatient() != null && bean.getPatient().length() > 0) {
				sql.append(" AND PATIENT like '" + bean.getPatient() + "%'");
			}
			if (bean.getDoctor() != null && bean.getDoctor().length() > 0) {
				sql.append(" AND DOCTOR like '" + bean.getDoctor() + "%'");
			}
			
			if (bean.getVisitDate() != null && bean.getVisitDate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getVisitDate().getTime());
				sql.append(" AND VISITDATE like '"+d+"%'");
			}
			
			
			if (bean.getFees()> 0) {
				sql.append(" AND FEES like '" + bean.getFees() + "%'");
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
				bean = new FollowUpBean();
				bean.setId(rs.getLong(1));
				bean.setPatient(rs.getString(2));
				bean.setDoctor(rs.getString(3));
				bean.setVisitDate(rs.getDate(4));
				bean.setFees(rs.getInt(5));
				
				
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
		StringBuffer sql = new StringBuffer("select * from ST_FOLLOWUP ");

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
				FollowUpBean bean = new FollowUpBean();
				bean.setId(rs.getLong(1));
				bean.setPatient(rs.getString(2));
				bean.setDoctor(rs.getString(3));
				bean.setVisitDate(rs.getDate(4));
				bean.setFees(rs.getInt(5));
				
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
