package de.hdm.itprojekt.coulddo.shared.bo;
import java.io.Serializable;

public class Lists extends BusinessObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String entry;
	private int ownerId = 0;
	private int catId = 0;
	private String listName;
	private String categoryName;
	
	
	public Lists() {
		
	}
	
	public Lists(String entry) {
		this.entry = entry;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getLists() {
		return entry;
	}
	
	public void setLists (String entry) {
		this.entry = entry;
	}

	public String getListName() {
		return listName;
	}
	
	public void setListName(String listName) {
		this.listName = listName;
	}
	
	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}
	
	public String getCatagoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
}
