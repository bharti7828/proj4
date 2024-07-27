package com.rays.pro4.Bean;

/**
 * Role JavaBean encapsulates Role attributes.
 * 
 * @author Muskan prajapat
 *
 */
public class LeadRoleBean extends BaseBean{

	public static final int GENERAL = 1;
	public static final int OBC = 2;
	public static final int  SC = 3;
	public static final int ST = 4;
	


	
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

