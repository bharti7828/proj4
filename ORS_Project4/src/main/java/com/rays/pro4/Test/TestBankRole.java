package com.rays.pro4.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.BankRoleBean;
import com.rays.pro4.Bean.RoleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.BankRoleModel;
import com.rays.pro4.Model.RoleModel;

public class TestBankRole {
	public static void main(String[] args) {
		testList();

	}

	public static void testList() {

		BankRoleModel model = new BankRoleModel();

		try {

			BankRoleBean bean = new BankRoleBean();
			List list = new ArrayList();
			list = model.list(0, 0);
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (BankRoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
