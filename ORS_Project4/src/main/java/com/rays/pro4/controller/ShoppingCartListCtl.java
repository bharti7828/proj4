
package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.ShoppingCartBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.ShoppingCartModel;
import com.rays.pro4.Model.StatusRoleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ShoppingCartListCtl", urlPatterns = { "/ctl/ShoppingCartListCtl" })
public class ShoppingCartListCtl extends BaseCtl {

	
	 @Override protected void preload(HttpServletRequest request) {
		HashMap map = new HashMap ();
		
		map.put("Dell", "Dell");
		map.put("Hp", "Hp");
		map.put("Asus", "Asus");
		map.put("Apple", "Apple");
		map.put("Lenovo", "Lenovo");
		
		request.setAttribute("product", map);
	  
		super.preload(request);
	 } 
	 
	 	@Override
	protected BaseBean populateBean(HttpServletRequest request) {


		 ShoppingCartBean bean = new ShoppingCartBean();

			bean.setId(DataUtility.getLong(request.getParameter("id")));
			System.out.println(bean.getId() + "idddddddddddddddddddddd");
			bean.setName(DataUtility.getString(request.getParameter("name")));

			bean.setProduct(DataUtility.getString(request.getParameter("product")));
			bean.setDate(DataUtility.getDate(request.getParameter("date")));

			bean.setQuantity(DataUtility.getLong(request.getParameter("quantity")));




		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		ShoppingCartBean bean = (ShoppingCartBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		ShoppingCartModel model = new ShoppingCartModel();

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
		ShoppingCartBean bean = (ShoppingCartBean) populateBean(request);
		String[] ids = request.getParameterValues("ids");

		ShoppingCartModel model = new ShoppingCartModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			System.out.println("qwerty");
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SHOPPINGCART_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SHOPPINGCART_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				ShoppingCartBean deletebean = new ShoppingCartBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ServletUtility.setSuccessMessage("ShoppingCart is Deleted Successfully", request);
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
		return ORSView.SHOPPINGCART_LIST_VIEW;
	}

}
