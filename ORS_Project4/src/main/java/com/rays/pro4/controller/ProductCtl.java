package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ProductModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ProductCtl", urlPatterns = { "/ctl/ProductCtl" })
public class ProductCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "name contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "dob"));
			pass = false;
		} /*
			 * else if (!DataValidator.isDate(request.getParameter("date"))) {
			 * request.setAttribute("date", "date contains date only"); pass = false; }
			 */

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "description"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("description"))) {
			request.setAttribute("description", "description contains alphabet only");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getValue("error.require", "price"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("price"))) {
			request.setAttribute("price", "price contains Number only");
			pass = false;

		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		ProductBean bean = new ProductBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setDiscription(DataUtility.getString(request.getParameter("discription")));
		bean.setPrice(DataUtility.getString(request.getParameter("price")));

		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		ProductModel model = new ProductModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("id=" + id);
		if (id > 0 || op != null) {
			System.out.println("id get for pctl");
			ProductBean bean;
			try {

				bean = model.findByPK(id);
				System.out.println(" FIND kIA");
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
		System.out.println("operation get kiya");
		long id = DataUtility.getLong(request.getParameter("id"));

		ProductModel model = new ProductModel();

		if (op.equals("save") || OP_UPDATE.equalsIgnoreCase(op)) {
			System.out.println("qwertyuiop");
			ProductBean bean = (ProductBean) populateBean(request);

			try {
				if (id > 0) {
					System.out.println("qwertyuiop");

					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("product is successfully Updated", request);

				} else {
					long pk = model.add(bean);
					bean.setId(pk);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("product is successfully Added", request);

				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("product already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ProductBean bean = (ProductBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.PRODUCT_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PRODUCT_VIEW;
	}
}

