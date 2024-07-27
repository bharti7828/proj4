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
import com.rays.pro4.Bean.OwnerBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;

import com.rays.pro4.Model.OwnerModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "OwnerCtl", urlPatterns = { "/ctl/OwnerCtl" })
public class OwnerCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(OwnerCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request) {
		HashMap map = new HashMap();
		map.put("NH256", "NH256");
		map.put("MP09250", "MP09250");
		map.put("DL09250", "DL09250");
		
		request.setAttribute("vehicleIDD", map);


		
		super.preload(request);
	}


	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		OwnerBean bean = new OwnerBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setVehicleID(DataUtility.getString(request.getParameter("vehicleID")));
		bean.setInsuranceAmout(DataUtility.getLong(request.getParameter("insuranceAmout")));

		return bean;

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} /*
			 * else if (!DataValidator.isName(request.getParameter("name"))) {
			 * request.setAttribute("name", " Name contains alphabet only "); pass = false;
			 * }
			 */
		
		
		 else if (DataValidator.isTooLong(request.getParameter("name"),20)) {
			  request.setAttribute("name", "Name must contain max 20 charactor ");
			  pass = false; 
			  }
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isAge(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Minimum Age 18 year"));
			pass = false;
		}


		if (DataValidator.isNull(request.getParameter("vehicleID"))) {
			request.setAttribute("vehicleID", PropertyReader.getValue("error.require", "vehicleID"));
			pass = false;
		}
		 
			
		if (DataValidator.isNull(request.getParameter("insuranceAmout"))) {
			request.setAttribute("insuranceAmout", PropertyReader.getValue("error.require", "insuranceAmout"));
			pass = false;

		}
		else if (DataValidator.isTooLong(request.getParameter("insuranceAmout"),16)) {
			  request.setAttribute("insuranceAmout", "InsuranceAmout must contain max 16 digit  ");
			  pass = false; }

		return pass;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("in get method");

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("=============================================");
		OwnerModel model = new OwnerModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);
		if (id > 0 || op != null) {
			System.out.println("-------------------------------------------------------");

			OwnerBean bean;
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

		OwnerModel model = new OwnerModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			OwnerBean bean = (OwnerBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);

					bean.setId(id);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");

					ServletUtility.setSuccessMessage("Owner is successfully Updated", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Owner is successfully Added", request);
					// ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Owner already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			OwnerBean bean = (OwnerBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.OWNER_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.OWNER_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.OWNER_VIEW;
	}

}
