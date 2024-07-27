package com.rays.pro4.Bean;

import java.util.Date;

public class ProjectBean extends BaseBean {
	
	private String name;
	private String category;
	private Date openDate;
	private double version;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version ;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return category+"";
	}
	
	

}
