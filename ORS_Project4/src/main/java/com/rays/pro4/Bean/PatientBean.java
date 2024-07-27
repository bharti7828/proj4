package com.rays.pro4.Bean;

import java.util.Date;

public class PatientBean extends BaseBean{
	
	
	private long id ;
	private String name;
	private Date dob;
	private long mobileNo;
	private String decease;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getDecease() {
		return decease;
	}

	public void setDecease(String decease) {
		this.decease = decease;
	}


	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return decease+"" ;
	}

}
