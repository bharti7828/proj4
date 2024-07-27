package com.rays.pro4.Bean;

/**
 * Role JavaBean encapsulates Role attributes.
 * 
 * @author Muskan prajapat
 *
 */
public class CategoryRoleBean extends BaseBean{

	public static final int Strategic = 1;
	public static final int Operational = 2;
	public static final int  Compliance = 3;
	
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

