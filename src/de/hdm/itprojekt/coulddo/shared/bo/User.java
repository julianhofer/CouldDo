package de.hdm.itprojekt.coulddo.shared.bo;


import com.google.gwt.user.client.rpc.IsSerializable;


public class User extends BusinessObject implements IsSerializable {


	private static final long serialVersionUID = 1L;

	
	private String username = "";
	private String email = "";
	
//CONSTRUCTORS================================================
	public User() {
		
	}
	
	public User (String emailAdress) {
		this.email = emailAdress;
	}
	
	public User (String username, String emailAdress) {
			this.username = username;
			this.email = emailAdress;
	}

//METHODS======================================================
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getId() {
		return this.id;
	}
}
