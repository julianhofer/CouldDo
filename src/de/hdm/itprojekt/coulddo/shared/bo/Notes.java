package de.hdm.itprojekt.coulddo.shared.bo;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class Notes extends BusinessObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String notes;
	private int ownerId = 0;
	private int catId = 0;
	private String noteName;
	private String categoryName;
	
	 
	public Notes() {
		
	}
	
	public Notes(String notes, String noteName, int catId) {
		this.notes= notes;
		setNoteName(noteName);
		setOwnerId(ownerId);
		setCatId(catId);
	}
	
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes (String notes) {
		this.notes = notes;
	}
	
	public String getNoteName() {
		return noteName;
	}
	
	public void setNoteName(String noteName) {
		this.noteName= noteName;
	}
	
	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
}

