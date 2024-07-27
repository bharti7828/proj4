package com.rays.pro4.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.ExpertiseRoleBean;
import com.rays.pro4.Bean.RoleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.ExpertiseRoleModel;
import com.rays.pro4.Model.RoleModel;

public class expertiseTest {
	public static void main(String[] args) {
		testList();

	}

	public static void testList() {

		ExpertiseRoleModel model = new ExpertiseRoleModel();

		try {

			ExpertiseRoleBean bean = new ExpertiseRoleBean();
			List list = new ArrayList();
			list = model.list(0, 0);
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (ExpertiseRoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
