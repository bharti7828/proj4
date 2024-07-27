package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ClientModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ClientCtl", urlPatterns = { "/ctl/ClientCtl" })
public class ClientCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(ClientCtl.class);
	

	 @Override protected void preload(HttpServletRequest request) {
		 HashMap map = new HashMap();
		 map.put("High", "High");
		 map.put("Middel", "Middel");
		 map.put("Low ", "Low");
		
		 request.setAttribute("priorityy", map);
		 super.preload(request);
	  
	 }	  



	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ClientBean bean = new ClientBean();

		System.out.println(request.getParameter("phoneNo"));

		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setPhoneNo(DataUtility.getLong(request.getParameter("phoneNo")));
		bean.setPriority(DataUtility.getString(request.getParameter("priority")));


		return bean;

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		}
	   if (DataValidator.isNull(request.getParameter("address"))) {
		request.setAttribute("address", PropertyReader.getValue("error.require", "address"));
		pass = false;
	
	
	}
		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "phoneNo"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))){
			request.setAttribute("phoneNo", " phoneNo. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}
		
		
		if (DataValidator.isNull(request.getParameter("priority"))) {
			request.setAttribute("priority", PropertyReader.getValue("error.require", "priority"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("priority"))) {
			request.setAttribute("priority", " priority contains alphabet only ");
			pass = false;
		}
		return pass; 
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in get method");

		String op = DataUtility.getString(request.getParameter("operation"));

		ClientModel model = new ClientModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			ClientBean bean;
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

		ClientModel model = new ClientModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ClientBean bean = (ClientBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Client is successfully Updated", request);

				} else {
					long pk = model.add(bean);
                    ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Client is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Client already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ClientBean bean = (ClientBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.CLIENT_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.CLIENT_VIEW;
	}

}
