package com.rays.pro4.Bean;

import java.util.Date;

public class SalaryBean extends BaseBean {

	private String employee;
	private int amount;
	private Date applieddate;
	private String Status;

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getApplieddate() {
		return applieddate;
	}

	public void setApplieddate(Date applieddate) {
		this.applieddate = applieddate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return employee;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return employee;
	}

}
