package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.LeadBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.JDBCDataSource;

public class LeadModel {
	
	public int nextPK()throws Exception{
		
				
		String sql = "SELECT MAX(ID) FROM ST_LEAD";
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
	
	public long add(LeadBean bean)throws Exception{
		
		Connection conn= JDBCDataSource.getConnection();
		int pk=0;
		pk=nextPK();
		PreparedStatement ps= conn.prepareStatement("INSERT INTO ST_LEAD VALUES(?,?,?,?,?)");
		
		ps.setLong(1,pk);
		ps.setString(2,bean.getName());
		ps.setDate(3, new Date(bean.getDob().getTime()));

		ps.setString(4,bean.getMobileNo());
		ps.setString(5,bean.getStatus());

		
		
		
		
		ps.executeUpdate();

		return pk;
		
		
	}
	
	public void update(LeadBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("UPDATE ST_LEAD SET NAME = ?, Dob = ?, MobileNo = ?, Status=? WHERE ID=?");
		
		ps.setString(1,bean.getName());
		ps.setDate(2, new Date(bean.getDob().getTime()));
		ps.setString(3,bean.getMobileNo());
		ps.setString(4,bean.getStatus());
		ps.setLong(5, bean.getId());

		ps.executeUpdate();
		
	}
	
	public void delete(LeadBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps= conn.prepareStatement("DELETE FROM ST_LEAD WHERE ID=?");
		
		ps.setLong(1,bean.getId());
		ps.executeUpdate();
	}
	public LeadBean findByPK(long pk) throws Exception {
	
	String sql = "SELECT * FROM ST_LEAD WHERE ID=?";
	LeadBean bean = null;
	Connection conn = null;
	
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new LeadBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setMobileNo(rs.getString(4));
			bean.setStatus(rs.getString(5));
			
						
			
			
		/* rs.close(); */
		}
      return bean;
}
	public List search(LeadBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_LEAD WHERE 1=1");
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getId()  > 0) {
				sql.append(" AND Id = '" + bean.getId());
			}
					   
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new java.sql.Date(bean.getDob().getTime());
				sql.append(" AND DOB like '"+d+"%'");
			}
			
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MobileNo like '" + bean.getMobileNo() + "%'");
			}
			
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND Status like '" + bean.getStatus() + "%'");
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
				bean = new LeadBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setMobileNo(rs.getString(4));
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


}