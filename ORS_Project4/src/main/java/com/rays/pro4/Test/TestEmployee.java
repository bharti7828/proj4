package com.rays.pro4.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.EmployeeRoleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.EmployeeRoleModel;

public class TestEmployee {
	public static void main(String[] args) {
		testList();

	}

	public static void testList() {

		EmployeeRoleModel model = new EmployeeRoleModel();

		try {

			EmployeeRoleBean bean = new EmployeeRoleBean();
			List list = new ArrayList();
			list = model.list(0, 0);
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (EmployeeRoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
