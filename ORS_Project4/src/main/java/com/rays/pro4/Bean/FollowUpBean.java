package com.rays.pro4.Bean;

import java.util.Date;

public class FollowUpBean extends BaseBean{
	
	private String patient;
	private String doctor;
	private Date visitDate;
	private int fees;
	
	
	public String getPatient() {
		return patient;
	}
	public void setPatient(String patient) {
		this.patient = patient;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public int getFees() {
		return fees;
	}
	public void setFees(int fees) {
		this.fees = fees;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return doctor;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return doctor;
	}
	

}
