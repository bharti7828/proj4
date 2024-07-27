package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import com.rays.pro4.Bean.User1Bean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Exception.RecordNotFoundException;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.EmailBuilder;
import com.rays.pro4.Util.EmailMessage;
import com.rays.pro4.Util.EmailUtility;
import com.rays.pro4.Util.JDBCDataSource;

/**
 * JDBC Implementation of User1Model.
 * @author Bharti Jaypal
 *
 */
public class User1Model {
	private static Logger log = Logger.getLogger(User1Model.class);

	public int nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");

		String sql = ("SELECT MAX(ID) FROM ST_USER1");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK Started");
		return pk + 1;

	}

	public long add(User1Bean bean) throws ApplicationException ,DuplicateRecordException{
		log.debug("Model add Started");

		String sql = "INSERT INTO ST_USER1 VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		User1Bean existbean = findByLogin(bean.getLogin());                               
		if (existbean != null) {
			throw new DuplicateRecordException("login Id already exists");

		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getLogin());
			pstmt.setString(4, bean.getPassword());
			// date of birth caste by sql date
			pstmt.setDate(5, new Date(bean.getDob().getTime()));

			int a = pstmt.executeUpdate();
			System.out.println(a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception ...", e);
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
		log.debug("Model Add End");
		return pk;

	}

	public void delete(User1Bean bean) throws ApplicationException {
		log.debug("Model delete start");
		String sql = "DELETE FROM ST_USER1 WHERE ID=?";
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
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete End");
	}

	public User1Bean findByLogin(String login) throws ApplicationException {
		log.debug("Model findByLohin Started");
		String sql = ("SELECT * FROM ST_USER1 WHERE login=?");
		User1Bean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new User1Bean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setLogin(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setDob(rs.getDate(5));

			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception .", e);
			throw new ApplicationException("Exception: Exception in getting User1 by Login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findby login end");
		return bean;
	}

	public User1Bean findByPK(long pk) throws ApplicationException {
		log.debug("Model findBy PK start");
		String sql = ("SELECT * FROM ST_USER1 WHERE ID=?");
		User1Bean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new User1Bean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setLogin(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setDob(rs.getDate(5));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception ", e);
			throw new ApplicationException("Exception : Exception in getting User1 by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method Find By PK end");
		return bean;
	}

	public void update(User1Bean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Start");
		String sql = "UPDATE ST_USER1 SET NAME=?,LOGIN=?, PASSWORD=?, DOB=? WHERE ID=?";
		Connection conn = null;
		User1Bean existBean = findByLogin(bean.getLogin());
		if (existBean != null && !(existBean.getId() == bean.getId())) {
			throw new DuplicateRecordException("LoginId is Already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getLogin());
			pstmt.setString(3, bean.getPassword());
			// date of birth caste by sql date
			pstmt.setDate(4, new Date(bean.getDob().getTime()));

			pstmt.setLong(5, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Update End ");
	}

	public List search(User1Bean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(User1Bean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Start");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER1 where 1=1");
		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
			}
			if (bean.getId() > 0) {
				sql.append(" AND Id = " + bean.getId());
			}

			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new java.sql.Date(bean.getDob().getTime());
				sql.append(" AND DOB like '"+d+"%'");
			}
		}
		//If page size is greater than zero then apply pagination
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
				bean = new User1Bean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setLogin(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setDob(rs.getDate(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("Exception: Exception in Search User1");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search end");
		return list;

	}

	public List getRoles(User1Bean bean) throws ApplicationException {
		log.debug("Model GetRoles Start");
		String sql = "SELECT * FROM ST_USER1 WHERE ROLE_ID=?";
		Connection conn = null;
		List list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new User1Bean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setLogin(rs.getString(3));
				bean.setPassword(rs.getString(4));
				bean.setDob(rs.getDate(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("DateBase Exception ", e);
			throw new ApplicationException("Exception: Exceptin in Get Roles");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Get Roles End");
		return list;

	}

}
