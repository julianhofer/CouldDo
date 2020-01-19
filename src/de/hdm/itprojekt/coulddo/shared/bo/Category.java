package de.hdm.itprojekt.coulddo.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;



public class Category extends BusinessObject implements IsSerializable {
	
//INITIALIZATION=============================================================

	private static final long serialVersionUID = 1L;
	private String name = "";
	private int noteId = 0;
	private int listId = 0;

//METHODS====================================================================
	
	
	public Category() {
		
	}
	
	public Category(String name, int noteId, int listId) {
		setCategoryName(name);
		this.listId = listId;
		this.noteId = noteId;
	}
	
	public String getCategoryName() {
		return name;
	}

	public void setCategoryName(String name) {
		this.name = name;
	}
	
	
	public int getNoteId() {
		return noteId;
	}
	
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	
	
	public int getListId() {
		return listId;
	}
	
	public void setListId(int listId) {
		this.listId = listId;
	}
	
	
	public int getId() {
		return this.id;
	}
	

}
