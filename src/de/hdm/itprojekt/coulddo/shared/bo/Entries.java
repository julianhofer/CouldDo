package de.hdm.itprojekt.coulddo.shared.bo;
import java.io.Serializable;
import java.sql.Array;



public class Entries extends BusinessObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String entry;
	private Integer listId = 0;
	
	
	public Entries() {
		
	}
	
	public Entries(String entry) {
		this.entry = entry;
	}

	public String getEntry() {
		return entry;
	}
	
	public void setEntry (String entry) {
		this.entry = entry;
	}

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}
	
}
