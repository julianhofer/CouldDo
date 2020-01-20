package de.hdm.itprojekt.coulddo.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;



public class Category extends BusinessObject implements IsSerializable {
	
//INITIALIZATION=============================================================

	private static final long serialVersionUID = 1L;
	private String name = "";
	private int ownerId = 0;


//METHODS====================================================================
	
	
	public Category() {
		
	}
	
	public Category(String name, int ownerId) {
		setCategoryName(name);
	}
	
	public String getCategoryName() {
		return name;
	}

	public void setCategoryName(String name) {
		this.name = name;
	}
	
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getId() {
		return this.id;
	}
	
}
