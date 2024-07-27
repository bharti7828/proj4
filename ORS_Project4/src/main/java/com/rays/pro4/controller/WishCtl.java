package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.WishBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.OrderRoleModel;
import com.rays.pro4.Model.ProductRoleModel;
import com.rays.pro4.Model.WishModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "WishCtl", urlPatterns = { "/ctl/WishCtl" })
public class WishCtl extends BaseCtl {
	// private static Logger log = Logger.getLogger(WishCtl.class);
	
	
	
	@Override
	protected void preload(HttpServletRequest request) {

		ProductRoleModel ppmodel = new ProductRoleModel();
		//WishModel Dmodel = new WishModel();

		try {
			 //List pList = pmodel.list(0, 0);
			List pplist = ppmodel.list(0, 0);
			
			//request.setAttribute("dob", DList);

			request.setAttribute("productt", pplist);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}



	

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("product"))) {
			request.setAttribute("product", PropertyReader.getValue("error.require", "quantity"));
			pass = false;
		}
//		} else if (!DataValidator.isName(request.getParameter("product"))) {
//			request.setAttribute("product", "Product contains alphabet only ");
//			pass = false;
//		}
		
	
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "date"));
			pass = false;
		} else if (!DataValidator.isAge(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Minimum Age 18 Year"));			
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "userName"));
			pass = false;
		
		
	   } else if (!DataValidator.isName(request.getParameter("userName"))) {
		request.setAttribute("userName", "UserName contains alphabet only ");
		pass = false;
	}
		if (DataValidator.isNull(request.getParameter("remark"))) {
			request.setAttribute("remark", PropertyReader.getValue("error.require", "remark"));
			pass = false;
		
		
	   } else if (!DataValidator.isName(request.getParameter("remark"))) {
		request.setAttribute("remark", "remark contains alphabet only ");
		pass = false;
	   }
		
		return pass; 
}

	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		WishBean bean = new WishBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		System.out.println(bean.getId() + "idddddddddddddddddddddd");
		bean.setProduct(DataUtility.getString(request.getParameter("product")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setRemark(DataUtility.getString(request.getParameter("remark")));


		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in get method");

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("=============================================");
		WishModel model = new WishModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);
		if (id > 0 || op != null) {
			System.out.println("-------------------------------------------------------");

			WishBean bean;
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

		WishModel model = new WishModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			WishBean bean = (WishBean) populateBean(request);

			try {
				if (id > 0) {
					 bean.setId(id);
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Wish is successfully Updated", request);
					

				} else {
					long pk = model.add(bean);
                    ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Wish is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			WishBean bean = (WishBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.WISH_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.WISH_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}
	@Override
	protected String getView() {
		return ORSView.WISH_VIEW;
	}

}
