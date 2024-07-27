package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.DoctorBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.DeceaseRoleModel;
import com.rays.pro4.Model.DoctorModel;
import com.rays.pro4.Model.ExpertiseRoleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "DoctorCtl", urlPatterns = { "/ctl/DoctorCtl" })
public class DoctorCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(DoctorCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request) {

		//DoctorRoleModel bmodel = new DoctorRoleModel();
		ExpertiseRoleModel emodel = new ExpertiseRoleModel();

		try {
			 List eList = emodel.list(0, 0);
			//List blist = bmodel.list(0, 0);
			
			request.setAttribute("Expertisee", eList);

			//request.setAttribute("DoctorList", blist);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}



	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		DoctorBean bean = new DoctorBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setExpertise(DataUtility.getString(request.getParameter("expertise")));

		return bean;

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", " Name contains alphabet only ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isAge(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Minimum Age 18 year"));
			pass = false;
		}


		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "mobileNo"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", " Mobile No. must be 10 Digit and No. Series start with 6-9 ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("expertise"))) {
			request.setAttribute("expertise", PropertyReader.getValue("error.require", "expertise"));
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
		DoctorModel model = new DoctorModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);
		if (id > 0 || op != null) {
			System.out.println("-------------------------------------------------------");

			DoctorBean bean;
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

		DoctorModel model = new DoctorModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			DoctorBean bean = (DoctorBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);

					bean.setId(id);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");

					ServletUtility.setSuccessMessage("Doctor is successfully Updated", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);


					ServletUtility.setSuccessMessage("Doctor is successfully Added", request);
					// ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Doctor already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			DoctorBean bean = (DoctorBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.DOCTOR_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.DOCTOR_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.DOCTOR_VIEW;
	}

}
