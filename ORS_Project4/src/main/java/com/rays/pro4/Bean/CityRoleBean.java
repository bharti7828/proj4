package com.rays.pro4.Bean;

/**
 * Role JavaBean encapsulates Role attributes.
 * 
 * @author Bharti Jaypal
 *
 */
public class CityRoleBean extends BaseBean{

	public static final int INDORE = 1;
	public static final int BHOPAL= 2;
	public static final int DELHI= 3;
	public static final int MUMBAI= 4;
	public static final int AGRA= 5;

	private String name;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
