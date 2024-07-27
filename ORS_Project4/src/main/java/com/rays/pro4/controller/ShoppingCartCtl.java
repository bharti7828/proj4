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
import com.rays.pro4.Bean.ShoppingCartBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ShoppingCartModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ShoppingCartCtl", urlPatterns = { "/ctl/ShoppingCartCtl" })
public class ShoppingCartCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {
		HashMap map = new HashMap();
		map.put("Dell", "Dell");
		map.put("Hp", "Hp");
		map.put("Asus", "Asus");
		map.put("Apple", "Apple");
		map.put("Lenovo", "Lenovo");
		request.setAttribute("productt", map);


		
		super.preload(request);
	}
	
	
	
	

	

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
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
			
		
		if (DataValidator.isNull(request.getParameter("product"))) {
			request.setAttribute("product", PropertyReader.getValue("error.require", "Product"));
			pass = false;
		
		}

		else if (!DataValidator.isName(request.getParameter("product"))) {
			request.setAttribute("product", " Product contains alphabet only ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));			
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity", PropertyReader.getValue("error.require", "Quantity"));
			pass = false;
		} 
		
		
		/*
		 * else if (!DataValidator.isLong(request.getParameter("quantity"))) {
		 * request.setAttribute("quantity", " quantity contains digit only "); pass=
		 * false; }
		 */	
		
		  else if (DataValidator.isTooLong(request.getParameter("quantity"),16)) {
		  request.setAttribute("quantity", "quantity must contain max 16 digit  ");
		  pass = false; }
		
		 
		 
		 
			 
		
		return pass; 
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
		
		System.out.println("in get method");

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("=============================================");
		ShoppingCartModel model = new ShoppingCartModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);
		if (id > 0 || op != null) {
			System.out.println("-------------------------------------------------------");

			ShoppingCartBean bean;
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

		ShoppingCartModel model = new ShoppingCartModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ShoppingCartBean bean = (ShoppingCartBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					
					bean.setId(id);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					
					ServletUtility.setSuccessMessage("ShoppingCart is successfully Updated", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);


					ServletUtility.setSuccessMessage("ShoppingCart is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Shopping already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ShoppingCartBean bean = (ShoppingCartBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.SHOPPINGCART_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.SHOPPINGCART_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.SHOPPINGCART_VIEW;
	}

}
