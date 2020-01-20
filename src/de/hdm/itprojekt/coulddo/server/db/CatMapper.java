package de.hdm.itprojekt.coulddo.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojekt.coulddo.shared.DatabaseException;
import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.Lists;
import de.hdm.itprojekt.coulddo.shared.bo.Notes;

public class CatMapper {
	
	private static CatMapper catMapper = null;

	/**
	 * Beugt mehrfach Instanzierung vor.
	 */
	protected CatMapper() {
	};

	public static CatMapper catMapper() {
		if (catMapper == null) {
			catMapper = new CatMapper();
		}
		return catMapper;
	}
	
	public Category insert(Category category) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;

		String maxId = "SELECT MAX(catId) AS maxid FROM categories";
		String insert = "INSERT INTO categories (catId, categoryName) VALUES (?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				category.setId(rs.getInt("maxid") + 1);
			}
			stmt = con.prepareStatement(insert);
			stmt.setInt(1, category.getId());
			stmt.setString(2, category.getCategoryName());
			stmt.executeUpdate();

		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return category;
	}
	
	public Category update(Category category) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String updateSQL = "UPDATE categories SET categoryName=? WHERE catId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, category.getCategoryName());
			stmt.setInt(2, category.getId());

			stmt.executeUpdate();
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return category;
	}
	
	
	// ToDo DELETE OR UPDATE ???
	/*
	 * public void deleteListFromCategory(Lists list, Category cat) throws
	 * DatabaseException {
	 * 
	 * Connection con = null; PreparedStatement stmt = null;
	 * 
	 * String delete = "DELETE FROM categories WHERE catId=? AND listId=?";
	 * 
	 * try { con = DBConnection.connection(); stmt = con.prepareStatement(delete);
	 * stmt.setInt(1, cat.getId()); stmt.setInt(2, list.getId());
	 * stmt.executeUpdate(); } catch (SQLException e2) { throw new
	 * DatabaseException(e2); } }
	 * 
	 * // ToDo DELETE OR UPDATE ??? public Category deleteNoteFromCategory(Notes
	 * note) throws DatabaseException { Connection con = null; PreparedStatement
	 * stmt = null;
	 * 
	 * String updateSQL = "UPDATE categories SET categoryName=? WHERE catId=?";
	 * 
	 * try { con = DBConnection.connection(); stmt =
	 * con.prepareStatement(updateSQL);
	 * 
	 * stmt.setString(1, category.getCategoryName()); stmt.setInt(2,
	 * category.getId());
	 * 
	 * stmt.executeUpdate(); } catch (SQLException e2) { throw new
	 * DatabaseException(e2); } return category; }
	 */
	
	
	public void delete(Category category) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String delete = "DELETE FROM categories WHERE catId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(delete);
			stmt.setInt(1, category.getId());

			stmt.executeUpdate();
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
	}
	
	public Category getCategoryById(int catId) throws DatabaseException {
		
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM categories WHERE catId=" + catId);
			
			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("catId"));
				category.setCategoryName(rs.getString("categoryName"));
				return category;
			}
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return null;
	}
	
	public Vector<Category> findAll() throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Category> categories = new Vector<Category>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM categories");

			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getInt("groupId"));
				category.setCategoryName(rs.getString("groupName"));

				categories.addElement(category);
			}
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return categories;
	}
	
	public Category addListToCategory(Lists list, Category cat) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;
		String clist = "INSERT INTO categories (catId, listId) VALUES (?,?)";
		Category category = new Category();
		category.setId(cat.getId());
		category.setId(list.getId());

		try {

			con = DBConnection.connection();
			stmt = con.prepareStatement(clist);
			stmt.setInt(1, category.getId());
			stmt.setInt(2, category.getId());
			stmt.executeUpdate();

			return category;
			
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
	}
	
	public Category addNoteToCategory(Notes note, Category cat) throws DatabaseException {

		Connection con = null;
		PreparedStatement stmt = null;
		String cnote = "INSERT INTO categories (catId, noteId) VALUES (?,?)";
		Category category = new Category();
		category.setId(cat.getId());
		category.setId(note.getId());

		try {

			con = DBConnection.connection();
			stmt = con.prepareStatement(cnote);
			stmt.setInt(1, category.getId());
			stmt.setInt(2, category.getId());
			stmt.executeUpdate();

			return category;
			
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
	}
	


}
