package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.DeceaseRoleBean;
import com.rays.pro4.Bean.DeceaseRoleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

/**
 * JDBC Implementation of BankRole Model.
 * 
 * @author Bharti Jaypal
 *
 */
public class DeceaseRoleModel {

//private static Logger log=Logger.getLogger(BankRoleModel.class);

	public Integer nextPK() throws DatabaseException {
		// log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_DECEASEROLE");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
		} catch (Exception e) {
			// log.error("Database Exception..",e);
			throw new DatabaseException("Exception : Exception in getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Modal nextPK End");
		return pk + 1;
	}

	public long add(DeceaseRoleBean bean) throws ApplicationException, DuplicateRecordException {

		// log.debug("Modal add Started");
		Connection conn = null;
		int pk = 0;

//		DeceaseRoleBean duplicateBankRole=findByName(bean.getName());
//		
//		if(duplicateBankRole !=null){
//			throw new DuplicateRecordException("BankRole already exists");
//			
//		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			System.out.println(pk + "in ModelJDBC");
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_DECEASEROLE VALUES(?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception...",e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// throw new ApplicationException("Exception : add rollback
				// exception"+ex.getMessage());
			}
			// throw new ApplicationException("Exception :Exception in add BankRole");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("Modal add End");
		return pk;
	}

	public void delete(DeceaseRoleBean bean) throws ApplicationException {

		// log.debug("Modal delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("Delete FROM ST_DECEASEROLE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			// log.error("Database Exception...",e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// throw new ApplicationException("Exception : Delete rollback
				// exception"+ex.getMessage());
			}
			// throw new ApplicationException("Exception : Exception in delete BankRole");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Modal delete Started");
	}

	public DeceaseRoleBean findByName(String name) throws ApplicationException {
		// log.debug("Modal findBy EmailId Started");
		StringBuffer sql = new StringBuffer("SELECT*FROM ST_DECEASEROLE WHERE NAME=?");

		DeceaseRoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new DeceaseRoleBean();
				bean.setId(1);
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
			}
			rs.close();

		} catch (Exception e) {
			// log.error("Database Exception..",e);
			// throw new ApplicationException("Exception : Exception in geting User by
			// emailId");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Modal findBy EmailId End");
		return bean;

	}

	public DeceaseRoleBean findByPK(long pk) throws ApplicationException {
		// log.debug("Modal findByPK Started");

		StringBuffer sql = new StringBuffer("SELECT*FROM ST_DECEASEROLE WHERE ID=?");
		DeceaseRoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new DeceaseRoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));

			}
			rs.close();
		} catch (Exception e) {
			// log.error("Database Exception..",e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Modal findByPK End");
		return bean;
	}

	public void update(DeceaseRoleBean bean) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model update Started");
		Connection conn = null;

		/*
		 * DeceaseRoleBean duplicataBankRole = findByName(bean.getName()); // Check if
		 * updated BankRole already exist if (duplicataBankRole != null &&
		 * duplicataBankRole.getId() != bean.getId()) { throw new
		 * DuplicateRecordException("BankRole already exists"); }
		 */
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_DECEASEROLE SET NAME=?,DESCRIPTION=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setLong(3, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating BankRole ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
// log.debug("Model update End");
	}

	public List search(DeceaseRoleBean bean) throws ApplicationException {
		return search(bean);
	}

	public List search(DeceaseRoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT*FROM ST_DECEASEROLE WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id= " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + bean.getDescription() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);

		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new DeceaseRoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
			}
			rs.close();
		} catch (Exception e) {
			// log.error("DatabaseException.....", e);
			// throw new ApplicationException("Exception : Exception in search BankRole");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model search End");
		return list;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model list Started");

		System.out.println("in list method");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_DECEASEROLE");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("in while loop");
				DeceaseRoleBean bean = new DeceaseRoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

}