
package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.TransactionBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.TransactionModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "TransactionCtl", urlPatterns = { "/ctl/TransactionCtl" })

public class TransactionCtl extends BaseCtl{
	
	 @Override protected void preload(HttpServletRequest request)
	 {
			
     
       HashMap map = new HashMap();
		map.put("prepaid", "prepaid");
		map.put("postpaid", "postpaid");
		 request.setAttribute("map",map );
		 
		 HashMap map1 = new HashMap();
			map1.put("BOI12309", "BOI12309");
			map1.put("HDFC784646", "HDFC784646");
		  request.setAttribute("map1", map1);
	  }

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("pooja"))) {
			request.setAttribute("pooja", PropertyReader.getValue("error.require", "Description"));
			pass = false;

		} 
			  else if (!DataValidator.isName(request.getParameter("pooja"))) {
			  request.setAttribute("pooja", "Description must contains alphabet only");
			  pass =false;
			  } 
			  else if (DataValidator.isTooLong(request.getParameter("pooja"),50)) {
					request.setAttribute("pooja", "Description must contain max 50 charactor  ");
					pass = false;
				}
			 
		if (DataValidator.isNull(request.getParameter("accountId"))) {
			request.setAttribute("accountId", PropertyReader.getValue("error.require", "AccountId"));
			pass = false;

		} 
			  

		if (DataValidator.isNull(request.getParameter("type"))) {
			request.setAttribute("type", PropertyReader.getValue("error.require", "Type"));
			pass = false;

		} 
			  else if (!DataValidator.isName(request.getParameter("type"))) {
			  request.setAttribute("type", "Type must contains alphabet only");
			  pass =false;
			  }
			 


		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}
		
		
		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TransactionBean bean = new TransactionBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		

		bean.setDescription(DataUtility.getString(request.getParameter("pooja")));
		bean.setDate(DataUtility.getDate(request.getParameter("dob")));
		
		bean.setType(DataUtility.getString(request.getParameter("type")));

		
		
		
		
		bean.setAccountId(DataUtility.getString(request.getParameter("accountId")));


		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Transaction ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
	
		TransactionModel model = new TransactionModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			TransactionBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><>**********" + id + op);

		TransactionModel model = new TransactionModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TransactionBean bean = (TransactionBean) populateBean(request);
			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" Transaction ctl DoPost 222222");
					ServletUtility.setSuccessMessage("Transaction is successfully Updated", request);

				} else {
					System.out.println(" Transaction ctl DoPost 33333");
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Transaction is successfully Added", request);
                    
					//bean.setId(pk);
					
				}
				ServletUtility.setBean(bean, request);
				
				
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" Transaction D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Transaction exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" Transaction ctl D p 5555555");

			TransactionBean bean = (TransactionBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" Transaction ctl D Post  6666666");
				ServletUtility.redirect(ORSView.TRANSACTION_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println("   ctl Do post 77777");

			ServletUtility.redirect(ORSView.TRANSACTION_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

	}


	@Override
	protected String getView() {
		return ORSView.TRANSACTION_VIEW;
	}

}


