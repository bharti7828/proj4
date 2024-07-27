package com.rays.pro4.Bean;

import java.util.Date;

public class BankBean  extends BaseBean{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private long id;
	private String Name;
	private Date dob;
	private long accountNo;
	private String bankName;


	
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}


	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+ "";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Name;
	}

}
