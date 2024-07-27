
package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.TransactionBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class TransactionModel {

	public int nextPK() throws Exception {

		String sql = "SELECT MAX(ID) FROM st_transaction";
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

	public long add(TransactionBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO st_transaction VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getDescription());
			pstmt.setDate(3, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(4, bean.getType());
			pstmt.setString(5, bean.getAccountId());
			
	
			// date of birth caste by sql date

			int a = pstmt.executeUpdate();
			System.out.println(a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				// application exception
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}

	public void delete(TransactionBean bean) throws ApplicationException {

		String sql = "DELETE FROM st_transaction WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public TransactionBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM st_transaction WHERE ID=?";
		TransactionBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TransactionBean();
				bean.setId(rs.getLong(1));
				bean.setDescription(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setType(rs.getString(4));
			    bean.setAccountId(rs.getString(5));
			

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public void update(TransactionBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE st_transaction SET description=?,date=?, type=?,accountId=?, WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

		
			pstmt.setString(1, bean.getDescription());
			pstmt.setDate(2, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(3, bean.getType());
			pstmt.setString(4, bean.getAccountId());
		
			pstmt.setLong(5, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List search(TransactionBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(TransactionBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM st_transaction where 1=1");
		if (bean != null) {
			
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND description like '" + bean.getDescription() + "%'");
			}
			
			if (bean.getAccountId() != null && bean.getAccountId().length() > 0) {
				sql.append(" AND accountId like '" + bean.getAccountId() + "%'");
			}
			
			if (bean.getType() != null && bean.getType().length() > 0) {
				sql.append(" AND TYPE like '" + bean.getType() + "%'");
			}

			if (bean.getDate() != null && bean.getDate().getTime() > 0) {
				Date d = new java.sql.Date(bean.getDate().getTime());
				sql.append(" AND DATE like '" + d + "%'");
			}

			
		
		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TransactionBean();
				bean.setId(rs.getLong(1));
				bean.setDescription(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setType(rs.getString(4));
			    bean.setAccountId(rs.getString(5));
			
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

	public List list(int pageNo, int pageSize) throws Exception {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_transaction");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println("preload........" + sql);
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				TransactionBean bean = new TransactionBean();
				bean.setId(rs.getLong(1));
				bean.setDescription(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setType(rs.getString(4));
			    bean.setAccountId(rs.getString(5));
			
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

