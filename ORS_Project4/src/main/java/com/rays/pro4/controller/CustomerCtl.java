package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.CustomerBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CityRoleModel;
import com.rays.pro4.Model.CustomerModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "CustomerCtl", urlPatterns = { "/ctl/CustomerCtl" })
public class CustomerCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(CustomerCtl.class);
	
	
	@Override
	protected void preload(HttpServletRequest request) {

		//CustomerRoleModel bmodel = new CustomerRoleModel();
		CityRoleModel cmodel = new CityRoleModel();

		try {
			 List cList = cmodel.list(0, 0);
			//List blist = bmodel.list(0, 0);
			
			request.setAttribute("cityy", cList);

			//request.setAttribute("CustomerList", blist);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	
	

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		CustomerBean bean = new CustomerBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		System.out.println(bean.getId() + "idddddddddddddddddddddd");
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setCompany(DataUtility.getString(request.getParameter("company")));
		bean.setPhoneNo(DataUtility.getLong(request.getParameter("phoneNo")));





		return bean;

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "firstName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", " firstName contains alphabet only ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "lastName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", " lastName contains alphabet only ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "dob"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "dob"));			
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("city"))) {
				request.setAttribute("city", PropertyReader.getValue("error.require", "city"));
				pass = false;
		}
			if (DataValidator.isNull(request.getParameter("company"))) {
				request.setAttribute("company", PropertyReader.getValue("error.require", "company"));
				pass = false;
			}
			
			if (DataValidator.isNull(request.getParameter("phoneNo"))) {
				request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "phoneNo"));
				pass = false;
			} else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
				request.setAttribute("phoneNo", " phoneNo. must be 10 Digit and No. Series start with 6-9 ");
				pass = false;
			}
			
		

		return pass; 
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in get method");

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("=============================================");
		CustomerModel model = new CustomerModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);
		if (id > 0 || op != null) {
			System.out.println("-------------------------------------------------------");

			CustomerBean bean;
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

		CustomerModel model = new CustomerModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CustomerBean bean = (CustomerBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					
					bean.setId(id);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					
					ServletUtility.setSuccessMessage("Customer is successfully Updated", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);


					ServletUtility.setSuccessMessage("Customer is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Customer already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CustomerBean bean = (CustomerBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.CUSTOMER_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.CUSTOMER_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.CUSTOMER_VIEW;
	}

}
