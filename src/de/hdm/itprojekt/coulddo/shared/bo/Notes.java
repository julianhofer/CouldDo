package de.hdm.itprojekt.coulddo.shared.bo;

import java.io.Serializable;

public class Notes extends BusinessObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String notes;
	private int ownerId = 0;
	
	public Notes() {
		
	}
	
	public Notes(String notes) {
		this.notes= notes;
	}
	
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getNotes() {
		return this.notes;
	}
	
	public void setNotes (String notes) {
		this.notes = notes;
	}
	
}

