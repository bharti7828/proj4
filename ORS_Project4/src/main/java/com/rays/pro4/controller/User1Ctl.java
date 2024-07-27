package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.User1Bean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Model.User1Model;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
 * The Class User1Ctl.
 * 
 * @author Bharti Jaypal
 * 
 */

@WebServlet(name = "User1Ctl", urlPatterns = { "/ctl/User1Ctl" })
public class User1Ctl extends BaseCtl {

	/* private static final long serialVersionUID = 1L; */

	//private static Logger log = Logger.getLogger(User11Ctl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("uctl preload");
		RoleModel model = new RoleModel();
		try {
			List l = model.list();
			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		User1Bean bean = new User1Bean();


		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));


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
			if (DataValidator.isNull(request.getParameter("login"))) {
				request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
				pass = false;
			} else if (DataValidator.isNotNull(request.getParameter("login"))
					&& !DataValidator.isEmail(request.getParameter("login"))) {


				request.setAttribute("login", "login id must be contains alphabet only");

				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
				pass = false;
			} else if (!DataValidator.isPassword(request.getParameter("password"))) {
				request.setAttribute("password",
						"Password contain 8 letters with alpha-numeric,capital latter & special Character");
				pass = false;
			}
			
			if (DataValidator.isNull(request.getParameter("dob"))) {
				request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
				pass = false;
			} else if (!DataValidator.isDate(request.getParameter("dob"))) {
				request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
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
		User1Model model = new User1Model();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);
		if (id > 0 || op != null) {
			System.out.println("-------------------------------------------------------");

			User1Bean bean;
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

		User1Model model = new User1Model();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			User1Bean bean = (User1Bean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					
					bean.setId(id);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					
					ServletUtility.setSuccessMessage("USER1 is successfully Updated", request);

				} else {
					long pk = model.add(bean);
                    ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("USER1 is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("USER1 already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			User1Bean bean = (User1Bean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.USER1_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.USER1_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.USER1_VIEW;
	}

}
