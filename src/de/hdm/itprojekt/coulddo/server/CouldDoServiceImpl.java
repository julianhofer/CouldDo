package de.hdm.itprojekt.coulddo.server;

import de.hdm.itprojekt.coulddo.client.CouldDoService;
import de.hdm.itprojekt.coulddo.server.db.CatMapper;
import de.hdm.itprojekt.coulddo.server.db.ListsMapper;
import de.hdm.itprojekt.coulddo.server.db.NotesMapper;
import de.hdm.itprojekt.coulddo.server.db.UserMapper;
import de.hdm.itprojekt.coulddo.shared.DatabaseException;
import de.hdm.itprojekt.coulddo.shared.FieldVerifier;
import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.Lists;
import de.hdm.itprojekt.coulddo.shared.bo.Notes;
import de.hdm.itprojekt.coulddo.shared.bo.User;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CouldDoServiceImpl extends RemoteServiceServlet implements CouldDoService {

	public CouldDoServiceImpl() throws IllegalArgumentException {

	}

	public String greetServer(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Serialisierung
	 */

	private static final long serialVersionUID = 1L;

	private UserMapper userMapper = null;
	private NotesMapper notesMapper = null;
	private ListsMapper listsMapper = null;
	private CatMapper catMapper = null;

	/**
	 * Initialization
	 */

	public void init() throws IllegalArgumentException {

		this.userMapper = UserMapper.userMapper();
		this.notesMapper = NotesMapper.notesMapper();
		this.listsMapper = ListsMapper.listsMapper();
		this.catMapper = CatMapper.catMapper();

	}

	/**
	 * USER
	 * 
	 * @param u User Objekt
	 * @return
	 * @throws Exception
	 */
	public User createUser(User u) throws Exception {
		if (u != null) {
			User user = userMapper.insert(u);
			return user;
		} else {
			return null;
		}
	}

	public User createUser(String emailAdress, String userName) throws IllegalArgumentException, DatabaseException {

		User user = new User();
		user.setEmail(emailAdress);
		user.setId(1);
		user.setUsername(userName);
		return this.userMapper.insert(user);
	}

	public void deleteUser(User u) throws IllegalArgumentException {

		try {
			userMapper.delete(u);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public User updateUser(User u) throws IllegalArgumentException, DatabaseException {

		try {
			return userMapper.update(u);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public User getUserById(int userId) throws IllegalArgumentException, DatabaseException {
		try {
			User foundUser = userMapper.getUserById(userId);
			return foundUser;

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public User getUserByEmail(String email) throws IllegalArgumentException, DatabaseException {
		try {
			return userMapper.findUserByEmail(email);
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public User getOwnProfile(User user) throws IllegalArgumentException, DatabaseException {
		try {

			return this.userMapper.getUserById(user.getId());

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * CATEGORY
	 * 
	 */
	public Category createGroup(Category c) throws IllegalArgumentException {
		try {
			return this.catMapper.insert(c);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);

		}
	}

	public void saveCategory(Category c) throws IllegalArgumentException {
		try {

			this.catMapper.update(c);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public void deleteCategory(Category c) throws IllegalArgumentException {
		try {

			this.catMapper.delete(c);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Category getCategoryById(int catId) throws IllegalArgumentException {
		try {

			return this.catMapper.getCategoryById(catId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

	}

	public Vector<Category> findAllCategories() throws IllegalArgumentException, DatabaseException {
		return catMapper.findAll();
	}

	/*
	 * public Category addNoteToCategory(Notes note, Category cat) throws
	 * IllegalArgumentException { try {
	 * 
	 * return this.catMapper.addNoteToCategory(note, cat);
	 * 
	 * } catch (IllegalArgumentException | DatabaseException e) {
	 * e.printStackTrace(); throw new IllegalArgumentException(e); } }
	 * 
	 * public Category addListToCategory(Lists list, Category cat) throws
	 * IllegalArgumentException { try {
	 * 
	 * return this.catMapper.addListToCategory(list, cat);
	 * 
	 * } catch (IllegalArgumentException | DatabaseException e) {
	 * e.printStackTrace(); throw new IllegalArgumentException(e); } }
	 */

	/*
	 * public void deleteListFromCategory(Lists list, Category cat) throws
	 * IllegalArgumentException { try {
	 * 
	 * this.catMapper.deleteListFromCategory(list, cat);
	 * 
	 * } catch (IllegalArgumentException | DatabaseException e) {
	 * e.printStackTrace(); throw new IllegalArgumentException(e); } }
	 * 
	 * public void deleteNoteFromCategory(Notes note, Category cat) throws
	 * IllegalArgumentException { try {
	 * 
	 * this.catMapper.update(note, cat);
	 * 
	 * } catch (IllegalArgumentException | DatabaseException e) {
	 * e.printStackTrace(); throw new IllegalArgumentException(e); } }
	 */

	/*
	 * public Vector<Category> findAllCategoriesByNoteId(int id) throws
	 * IllegalArgumentException { try { return
	 * catMapper.findAllCategoriesByNoteId(id);
	 * 
	 * } catch (IllegalArgumentException | DatabaseException e) {
	 * e.printStackTrace(); throw new IllegalArgumentException(e); } }
	 * 
	 * public Vector<Category> findAllCategoriesByListId(int id) throws
	 * IllegalArgumentException { try { return
	 * catMapper.findAllCategoriesByListId(id);
	 * 
	 * } catch (IllegalArgumentException | DatabaseException e) {
	 * e.printStackTrace(); throw new IllegalArgumentException(e); } }
	 */

	/**
	 * NOTES
	 * 
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 */

	public Notes createNote(Notes n) throws IllegalArgumentException {
		try {

			return this.notesMapper.insert(n);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Notes saveNote(Notes n) throws IllegalArgumentException {
		try {

			this.notesMapper.update(n);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return n;
	}

	public Notes deleteNote(Notes n) throws IllegalArgumentException {
		try {

			this.notesMapper.delete(n);

		} catch (IllegalArgumentException | DatabaseException e) {
			throw new IllegalArgumentException(e);
		}
		return n;
	}

	public Notes getNoteById(int noteId) throws IllegalArgumentException {
		try {

			return this.notesMapper.findById(noteId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/*
	 * public Vector<Notes> getAllNotesByCatId(int catId) throws
	 * IllegalArgumentException { try { return
	 * notesMapper.getAllNotesByCatId(catId); } catch (IllegalArgumentException |
	 * DatabaseException e) { e.printStackTrace(); throw new
	 * IllegalArgumentException(e); } }
	 */

	public Vector<Notes> findAllNotes() throws IllegalArgumentException {
		try {
			return notesMapper.findAllNotes();
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/*
	 * public Vector<Notes> findAllNotesByUserId(int ownerId) throws
	 * IllegalArgumentException { try { return
	 * notesMapper.findAllNotesByUserId(ownerId);
	 * 
	 * } catch (IllegalArgumentException | DatabaseException e) {
	 * e.printStackTrace(); throw new IllegalArgumentException(e); } }
	 */

	public Vector<Notes> findAllNotesByUserIdAndCatId(int ownerId, int catId) throws IllegalArgumentException {
		try {
			return notesMapper.findAllNotesByUserIdAndCatId(ownerId, catId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * LISTS
	 */

	public Lists createList(Lists l) throws IllegalArgumentException {
		try {

			return this.listsMapper.insert(l);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	public Lists saveList(Lists l) throws IllegalArgumentException {
		try {

			this.listsMapper.update(l);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return l;
	}

	public Lists deleteList(Lists l) throws IllegalArgumentException {
		try {

			this.listsMapper.delete(l);

		} catch (IllegalArgumentException | DatabaseException e) {
			throw new IllegalArgumentException(e);
		}
		return l;
	}

	public Lists getListById(int listId) throws IllegalArgumentException {
		try {

			return this.listsMapper.findById(listId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/*
	 * public Vector<Lists> getAllListsByCatId(int catId) throws
	 * IllegalArgumentException { try { return
	 * listsMapper.getAllListsByCatId(catId); } catch (IllegalArgumentException |
	 * DatabaseException e) { e.printStackTrace(); throw new
	 * IllegalArgumentException(e); } }
	 */

	public Vector<Lists> findAllLists() throws IllegalArgumentException {
		try {
			return listsMapper.findAllLists();
		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	/*
	 * public Vector<Lists> findAllListsByUserId(int ownerId) throws
	 * IllegalArgumentException { try { return
	 * listsMapper.findAllListsByUserId(ownerId);
	 * 
	 * } catch (IllegalArgumentException | DatabaseException e) {
	 * e.printStackTrace(); throw new IllegalArgumentException(e); } }
	 */

	public Vector<Lists> findAllListsByUserIdAndCatId(int ownerId, int catId) throws IllegalArgumentException {
		try {
			return listsMapper.findAllListsByUserIdAndCatId(ownerId, catId);

		} catch (IllegalArgumentException | DatabaseException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

}
