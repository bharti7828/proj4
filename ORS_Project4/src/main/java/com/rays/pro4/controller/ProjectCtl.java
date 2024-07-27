package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ProjectBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CategoryRoleModel;
import com.rays.pro4.Model.LeadRoleModel;
import com.rays.pro4.Model.ProjectModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ProjectCtl", urlPatterns = { "/ctl/ProjectCtl" })
public class ProjectCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(ProjectCtl.class);

	
	
	@Override
	protected void preload(HttpServletRequest request) {

		CategoryRoleModel cmodel = new CategoryRoleModel();
		//LeadModel model = new LeadModel();

		try {
			List clist = cmodel.list(0,0);
			
			request.setAttribute("categoryy", clist);
			
			//request.setAttribute("List", dlist);

		
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ProjectBean bean = new ProjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		System.out.println(bean.getId() + "idddddddddddddddddddddd");
		bean.setName(DataUtility.getString(request.getParameter("name")));
	bean.setCategory(DataUtility.getString(request.getParameter("category")));
		bean.setOpenDate(DataUtility.getDate(request.getParameter("openDate")));
		bean.setVersion(DataUtility.getDouble(request.getParameter("version")));
	

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
		if (DataValidator.isNull(request.getParameter("category"))) {
			request.setAttribute("category", PropertyReader.getValue("error.require", "category"));
			pass = false;
		if (DataValidator.isNull(request.getParameter("openDate"))) {
			request.setAttribute("openDate", PropertyReader.getValue("error.require", "openDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("openDate"))) {
			request.setAttribute("openDate", PropertyReader.getValue("error.require", "openDate"));			
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("version"))) {
			request.setAttribute("version", PropertyReader.getValue("error.require", "version"));
			pass = false;
		} 
			
			/*
			 * else if (!DataValidator.isInteger(request.getParameter("version"))) {
			 * request.setAttribute("version", " version contains digit only"); pass =
			 * false; }
			 */
		
		}
		return pass; 
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in get method");

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("=============================================");
		ProjectModel model = new ProjectModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);
		if (id > 0 || op != null) {
			System.out.println("-------------------------------------------------------");

			ProjectBean bean;
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

		ProjectModel model = new ProjectModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ProjectBean bean = (ProjectBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					
					bean.setId(id);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					
					ServletUtility.setSuccessMessage("Project is successfully Updated", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);


					ServletUtility.setSuccessMessage("Project is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Project already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ProjectBean bean = (ProjectBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.PROJECT_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.PROJECT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.PROJECT_VIEW;
	}

}
