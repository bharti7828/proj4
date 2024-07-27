package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.FollowUpModel;
import com.rays.pro4.Model.FollowUpRoleModel;
//import com.rays.pro4.Model.FollowUpRoleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "FollowUpListCtl", urlPatterns = { "/ctl/FollowUpListCtl" })

public class FollowUpListCtl extends BaseCtl {
	
//	private static Logger log = Logger.getLogger(FollowUpListCtl.class);
	
	
	
	
	@Override
	protected void preload(HttpServletRequest request) {

		FollowUpRoleModel pmodel = new FollowUpRoleModel();
		FollowUpModel dmodel = new FollowUpModel();

		try {
			List plist = pmodel.list(0,0);
			List dlist = dmodel.list(0,0);
			
			request.setAttribute("patient", plist);
			request.setAttribute("doctor", dlist);
			
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;

		int pageNo = 1;
		int pageSize = 10;

		FollowUpBean bean = (FollowUpBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		FollowUpModel model = new FollowUpModel();

		try {
			list = model.search(bean, pageNo, pageSize);
		} catch (Exception e) {
			ServletUtility.handleException(e, request, response);
		}

		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}

		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("search");
		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		String op = DataUtility.getString(request.getParameter("operation"));
		FollowUpBean bean = (FollowUpBean) populateBean(request);
		String[] ids = request.getParameterValues("ids");

		FollowUpModel model = new FollowUpModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			System.out.println("qwerty");
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FOLLOWUP_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FOLLOWUP_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				FollowUpBean deletebean = new FollowUpBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ServletUtility.setSuccessMessage("FollowUp is Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			System.out.println("qwerty1");
			list = model.search(bean, pageNo, pageSize);
			System.out.println("1234567");
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FOLLOWUP_LIST_VIEW;
	}

}
