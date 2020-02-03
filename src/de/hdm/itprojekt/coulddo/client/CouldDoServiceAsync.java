package de.hdm.itprojekt.coulddo.client;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.Lists;
import de.hdm.itprojekt.coulddo.shared.bo.Notes;
import de.hdm.itprojekt.coulddo.shared.bo.User;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CouldDoServiceAsync {
	
	
	void init(AsyncCallback<Void> callback);
	
	// USER

	void createUser(User u, AsyncCallback<User> callback);

    void createsUser(String emailAdress, String userName, AsyncCallback<User> callback);

	void deleteUser(User u, AsyncCallback<Void> callback);

	void updateUser(User u, AsyncCallback<User> callback);

	void getUserById(int userId, AsyncCallback<User> callback);

	void getUserByEmail(String email, AsyncCallback<User> callback);

	void getOwnProfile(User user, AsyncCallback<User> callback);
	
	
	// CATEGORY

	void createCategory(Category c, AsyncCallback<Category> callback);

	void saveCategory(Category c, AsyncCallback<Void> callback);

	void deleteCategory(Category c, AsyncCallback<Void> callback);

	void getCategoryByUserId(int ownerId, AsyncCallback<Vector<Category>> callback);

	void findAllCategories(AsyncCallback<Vector<Category>> callback);
	
	
	// NOTES

	void createNote(Notes n, AsyncCallback<Notes> callback);

	void saveNote(Notes n, AsyncCallback<Notes> callback);

	void deleteNote(Notes n, AsyncCallback<Notes> callback);

	void getNoteById(int noteId, AsyncCallback<Notes> callback);

	void findAllNotes(AsyncCallback<Vector<Notes>> callback);

	void findAllNotesByUserIdAndCatVector(int ownerId, Vector<Category> cat, AsyncCallback<Vector<Notes>> callback);
	
	
	// LISTS

	void createList(Lists l, AsyncCallback<Lists> callback);

	void saveList(Lists l, AsyncCallback<Lists> callback);

	void deleteList(Lists l, AsyncCallback<Lists> callback);

	void getListById(int listId, AsyncCallback<Lists> callback);

	void findAllLists(AsyncCallback<Vector<Lists>> callback);

	void findAllListsByUserIdAndCatVector(int ownerId, Vector<Category> cat, AsyncCallback<Vector<Lists>> callback);
	

	
}
