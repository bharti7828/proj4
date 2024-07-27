package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.JobBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.JobModel;
import com.rays.pro4.Model.StatusRoleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "JobCtl", urlPatterns = { "/ctl/JobCtl" })
public class JobCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {
		HashMap map = new HashMap();
		map.put("Open", "Open");
		map.put("Close", "Close");
		map.put("Hold", "Hold");
		request.setAttribute("statuss", map);


		
		super.preload(request);
	}
	
	
	
	

	

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "Title"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("title"))) {
			request.setAttribute("title", " Title contains alphabet only ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dateOfOpening"))) {
			request.setAttribute("dateOfOpening", PropertyReader.getValue("error.require", "DateOfOpening"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dateOfOpening"))) {
			request.setAttribute("dateOfOpening", PropertyReader.getValue("error.require", "DateOfOpening"));			
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("experience"))) {
			request.setAttribute("experience", PropertyReader.getValue("error.require", "Experience"));
			pass = false;
		} 
		
		/*
		 * else if (!DataValidator.isName(request.getParameter("experience"))) {
		 * request.setAttribute("experience", " experience contains digit only "); pass
		 * = false; }
		 */
		 
			 
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		
		}

		return pass; 
	}
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		JobBean bean = new JobBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		System.out.println(bean.getId() + "idddddddddddddddddddddd");
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setDateOfOpening(DataUtility.getDate(request.getParameter("dateOfOpening")));
		bean.setExperience(DataUtility.getString(request.getParameter("experience")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));


		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in get method");

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("=============================================");
		JobModel model = new JobModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);
		if (id > 0 || op != null) {
			System.out.println("-------------------------------------------------------");

			JobBean bean;
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

		JobModel model = new JobModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			JobBean bean = (JobBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					
					bean.setId(id);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					
					ServletUtility.setSuccessMessage("Job is successfully Updated", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);


					ServletUtility.setSuccessMessage("Job is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Job already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			JobBean bean = (JobBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.JOB_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.JOB_VIEW;
	}

}
