package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.JDBCDataSource;

public class EmployeeModel {
	
	public int nextPK()throws Exception{
		
				
		String sql = "SELECT MAX(ID) FROM ST_EMPLOYEE";
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
	
	public long add(EmployeeBean bean)throws Exception{
		
		Connection conn= JDBCDataSource.getConnection();
		int pk=0;
		pk=nextPK();
		PreparedStatement ps= conn.prepareStatement("INSERT INTO ST_EMPLOYEE VALUES(?,?,?,?)");
		
		ps.setLong(1,pk);
		ps.setString(2,bean.getName());
		ps.setString(3,bean.getSalary());
		ps.setString(4,bean.getDepartment());
		ps.executeUpdate();

		return pk;
		
		
	}
	
	public void update(EmployeeBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("UPDATE ST_EMPLOYEE SET NAME = ?, Salary = ?, Department = ? WHERE ID=?");
		
		ps.setString(1,bean.getName());
		ps.setString(2,bean.getSalary());
		ps.setString(3,bean.getDepartment());
		ps.setLong(4,bean.getId());
		
		ps.executeUpdate();
		
	}
	
	public void delete(EmployeeBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps= conn.prepareStatement("DELETE FROM ST_EMPLOYEE WHERE ID=?");
		
		ps.setLong(1,bean.getId());
		ps.executeUpdate();
	}
	public EmployeeBean findByPK(long pk) throws Exception {
	
	String sql = "SELECT * FROM ST_EMPLOYEE WHERE ID=?";
	EmployeeBean bean = null;
	Connection conn = null;
	
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new EmployeeBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setSalary(rs.getString(3));
			bean.setDepartment(rs.getString(4));
			
						
			
			
		/* rs.close(); */
		}
      return bean;
}
	public List search(EmployeeBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_EMPLOYEE WHERE 1=1");
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getId()  > 0) {
				sql.append(" AND Id = '" + bean.getId());
			}
					   
			if (bean.getSalary() != null && bean.getSalary().length() > 0) {
				sql.append(" AND Salary like '" + bean.getSalary() + "%'");
			}
			
			if (bean.getDepartment() != null && bean.getDepartment().length() > 0) {
				sql.append(" AND Department like '" + bean.getDepartment() + "%'");
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
				bean = new EmployeeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setSalary(rs.getString(3));
				bean.setDepartment(rs.getString(4));
			
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