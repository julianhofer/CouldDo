package de.hdm.itprojekt.coulddo.shared.bo;
import java.io.Serializable;

public class Lists extends BusinessObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String entry;
	private int ownerId = 0;
	private String listName;

	public Lists() {
		
	}
	
	public Lists(String entry) {
		this.entry= entry;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getLists() {
		return this.entry;
	}
	
	public void setLists (String entry) {
		this.entry = entry;
	}

	public String getListName() {
		return null;
	}
	
	public void setListName(String listName) {
		this.listName = listName;
	}
	
}
