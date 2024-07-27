
package com.rays.pro4.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.WishBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ProductRoleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.WishModel;
import com.rays.pro4.Model.ExpertiseRoleModel;
import com.rays.pro4.Model.ProductRoleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "WishListCtl", urlPatterns = { "/ctl/WishListCtl" })
public class WishListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		ProductRoleModel pmodel = new ProductRoleModel();
		//WishModel Dmodel = new WishModel();

		try {
			 //List pList = pmodel.list(0, 0);
			List plist = pmodel.list(0, 0);
			
			//request.setAttribute("dob", DList);

			request.setAttribute("product", plist);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		WishBean bean = new WishBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setProduct(DataUtility.getString(request.getParameter("product")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setRemark(DataUtility.getString(request.getParameter("remark")));

		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		WishBean bean = (WishBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		WishModel model = new WishModel();

		System.out.println(bean);
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
		WishBean bean = (WishBean) populateBean(request);
		String[] ids = request.getParameterValues("ids");

		WishModel model = new WishModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			System.out.println("qwerty");
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WISH_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WISH_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				WishBean deletebean = new WishBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ServletUtility.setSuccessMessage("Wish is Deleted Successfully", request);
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
		return ORSView.WISH_LIST_VIEW;
	}

}
