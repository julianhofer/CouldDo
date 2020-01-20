package de.hdm.itprojekt.coulddo.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojekt.coulddo.shared.DatabaseException;
import de.hdm.itprojekt.coulddo.shared.bo.*;



/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("coulddoservice")
public interface CouldDoService extends RemoteService {
	

	public void init() throws IllegalArgumentException;
	
	// USER
	
	public User createUser(User u) throws IllegalArgumentException, Exception;

	public User createUser(String emailAdress, String userName) throws IllegalArgumentException, DatabaseException;

	public void deleteUser(User u) throws IllegalArgumentException, DatabaseException;

	public User updateUser(User u) throws IllegalArgumentException, DatabaseException;

	public User getUserById(int userId) throws IllegalArgumentException, DatabaseException;

	public User getUserByEmail(String email) throws IllegalArgumentException, DatabaseException, IllegalArgumentException;

	public User getOwnProfile(User user) throws IllegalArgumentException, DatabaseException;
	
	
	// Category
	
	public Category createCategory(Category c) throws IllegalArgumentException, DatabaseException;
	
	public void saveCategory(Category c) throws IllegalArgumentException, DatabaseException;
	
	public void deleteCategory(Category c) throws IllegalArgumentException, DatabaseException;
	
	public Category getCategoryById(int catId) throws IllegalArgumentException, DatabaseException;
	
	public Vector<Category> findAllCategories() throws IllegalArgumentException, DatabaseException;
	
	
	// NOTES 
	
	public Notes createNote(Notes n) throws IllegalArgumentException, DatabaseException;
	
	public Notes saveNote(Notes n) throws IllegalArgumentException, DatabaseException; 
	
	public Notes deleteNote(Notes n) throws IllegalArgumentException, DatabaseException;
	
	public Notes getNoteById(int noteId) throws IllegalArgumentException, DatabaseException;
	
	public Vector<Notes> findAllNotes() throws IllegalArgumentException, DatabaseException;
	
	public Vector<Notes> findAllNotesByUserIdAndCatId(int ownerId, int catId) throws IllegalArgumentException, DatabaseException;
	
	
	// LISTS
	
	public Lists createList(Lists l) throws IllegalArgumentException, DatabaseException;
	
	public Lists saveList(Lists l) throws IllegalArgumentException, DatabaseException;
	
	public Lists deleteList(Lists l) throws IllegalArgumentException, DatabaseException;
	
	public Lists getListById(int listId) throws IllegalArgumentException, DatabaseException;
	
	public Vector<Lists> findAllLists() throws IllegalArgumentException, DatabaseException;
	
	public Vector<Lists> findAllListsByUserIdAndCatId(int ownerId, int catId) throws IllegalArgumentException, DatabaseException;
	
}


