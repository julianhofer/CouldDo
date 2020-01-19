package de.hdm.itprojekt.coulddo.shared;

import java.io.Serializable;

public class DatabaseException extends Exception implements Serializable {
	public DatabaseException(Exception e) {
		super(e.getMessage());
	}

	public DatabaseException() {

	}

}
