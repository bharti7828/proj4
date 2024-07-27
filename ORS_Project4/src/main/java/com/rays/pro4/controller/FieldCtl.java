package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.FieldBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.FieldModel;
import com.rays.pro4.Model.FieldRoleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "FieldCtl", urlPatterns = { "/ctl/FieldCtl" })
public class FieldCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(FieldCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request) {

		FieldRoleModel fmodel = new FieldRoleModel();

		try {
			List flist = fmodel.list(0, 0);
			

			request.setAttribute("TypeListt", flist);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	
	
	
	

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		FieldBean bean = new FieldBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		System.out.println(bean.getId() + "idddddddddddddddddddddd");
		bean.setLabel(DataUtility.getString(request.getParameter("label")));
		bean.setType(DataUtility.getString(request.getParameter("type")));
		bean.setActive(DataUtility.getString(request.getParameter("active")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));


		return bean;

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("label"))) {
			request.setAttribute("label", PropertyReader.getValue("error.require", "label"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("label"))) {
			request.setAttribute("label", " label contains alphabet only ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("type"))) {
			request.setAttribute("type", PropertyReader.getValue("error.require", "type"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("active"))) {
			request.setAttribute("active", PropertyReader.getValue("error.require", "active"));
			pass = false;
		}	if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "description"));
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
		FieldModel model = new FieldModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);
		if (id > 0 || op != null) {
			System.out.println("-------------------------------------------------------");

			FieldBean bean;
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

		FieldModel model = new FieldModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FieldBean bean = (FieldBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					
					bean.setId(id);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					
					ServletUtility.setSuccessMessage("Field is successfully Updated", request);

				} else {
					long pk = model.add(bean);
                    ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Field is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Field already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			FieldBean bean = (FieldBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.FIELD_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.FIELD_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.FIELD_VIEW;
	}

}
