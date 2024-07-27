package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;

import com.rays.pro4.Model.FollowUpModel;
import com.rays.pro4.Model.FollowUpRoleModel;

import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "FollowUpCtl", urlPatterns = { "/ctl/FollowUpCtl" })
public class FollowUpCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(FollowupCtl.class);
	
	
	
	@Override
	protected void preload(HttpServletRequest request) {

		FollowUpRoleModel pmodel = new FollowUpRoleModel();
	//	FollowUpModel dmodel = new FollowUpModel();

		try {
			List plist = pmodel.list(0,0);
			//List dlist = dmodel.list(0,0);
			request.setAttribute("patientt", plist);
		//	request.setAttribute("doctor", dlist);
			//request.setAttribute("List", dlist);

		
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		FollowUpBean bean = new FollowUpBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPatient(DataUtility.getString(request.getParameter("patient")));
		bean.setDoctor(DataUtility.getString(request.getParameter("doctor")));
		bean.setVisitDate(DataUtility.getDate(request.getParameter("visitDate")));
		bean.setFees(DataUtility.getInt(request.getParameter("fees")));


		return bean;

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("patient"))) {
			request.setAttribute("patient", PropertyReader.getValue("error.require", "patient"));
			pass = false;
		
		if (DataValidator.isNull(request.getParameter("doctor"))) {
			request.setAttribute("doctor", PropertyReader.getValue("error.require", "doctor"));
			pass = false;
		}
//		 else if (!DataValidator.isName(request.getParameter("doctor"))) {
//			request.setAttribute("doctor", " doctor cotains only alphabate");
//			pass = false;
//		}

		if (DataValidator.isNull(request.getParameter("visitDate"))) {
			request.setAttribute("visitDate", PropertyReader.getValue("error.require", "visitDate"));
			pass = false;
		} else if (!DataValidator.isAge(request.getParameter("visitDate"))) {
			request.setAttribute("visitDate", PropertyReader.getValue("error.require", "Minimum Age 18 Year"));			
			pass = false;
		}
	
		
		if (DataValidator.isNull(request.getParameter("fees"))) {
			request.setAttribute("fees", PropertyReader.getValue("error.require", "fees"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("fees"))) {
			request.setAttribute("fees", " must cotains digit only ");
			pass = false;
		}
		}
		return pass; 
	}
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in get method");

		String op = DataUtility.getString(request.getParameter("operation"));

		FollowUpModel model = new FollowUpModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			FollowUpBean bean;
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

		FollowUpModel model = new FollowUpModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FollowUpBean bean = (FollowUpBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Followup is successfully Updated", request);

				} else {
					long pk = model.add(bean);
                    ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Followup is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Followup already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			FollowUpBean bean = (FollowUpBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.FOLLOWUP_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.FOLLOWUP_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.FOLLOWUP_VIEW;
	}

}
