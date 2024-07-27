package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.EmployeeModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "EmployeeCtl", urlPatterns = { "/ctl/EmployeeCtl" })
public class EmployeeCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(EmployeeCtl.class);

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		EmployeeBean bean = new EmployeeBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setSalary(DataUtility.getString(request.getParameter("salary")));
		bean.setDepartment(DataUtility.getString(request.getParameter("department")));

		return bean;

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", " name contains alphabet only ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("salary"))) {
			request.setAttribute("salary", PropertyReader.getValue("error.require", "salary"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("salary"))) {
			request.setAttribute("salary", " salary contains digit only ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("department"))) {
			request.setAttribute("department", PropertyReader.getValue("error.require", "department"));
			pass = false;
		} else if (!DataValidator.isNull(request.getParameter(""))) {
			request.setAttribute("department", " department contains alphabet only ");
			pass = false;
		}
		return pass; 
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in get method");

		String op = DataUtility.getString(request.getParameter("operation"));

		EmployeeModel model = new EmployeeModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			EmployeeBean bean;
			try {

				bean = model.findByPK(id);
				System.out.println(" FIND kiya");
				System.out.println(bean);
				ServletUtility.setBean(bean, request);

				ServletUtility.forward(getView(), request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;

		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		EmployeeModel model = new EmployeeModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			EmployeeBean bean = (EmployeeBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Employee is successfully Updated", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					

					ServletUtility.setSuccessMessage("Employee is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Employee already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			EmployeeBean bean = (EmployeeBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.EMPLOYEE_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.EMPLOYEE_VIEW;
	}

}
