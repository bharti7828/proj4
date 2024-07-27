package com.rays.pro4.Bean;

import java.util.Date;

public class OwnerBean  extends BaseBean{
	
	private String name;
	private Date dob;
	private String vehicleID;
	private long insuranceAmout;

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

	public String getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}

	public long getInsuranceAmout() {
		return insuranceAmout;
	}

	public void setInsuranceAmout(long insuranceAmout) {
		this.insuranceAmout = insuranceAmout;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return dob+"";
	}

}
