package de.hdm.itprojekt.coulddo.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojekt.coulddo.shared.DatabaseException;
import de.hdm.itprojekt.coulddo.shared.bo.Lists;


public class ListsMapper {

	private static ListsMapper listsMapper = null;

	/**
	 * Beugt mehrfach Instanzierung vor.
	 */
	protected ListsMapper() {
	};

	public static ListsMapper listsMapper() {
		if (listsMapper == null) {
			listsMapper = new ListsMapper();
		}
		return listsMapper;
	}

	public Lists insert(Lists list) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String maxId = "SELECT MAX(listId) AS maxid FROM lists";

		String insert = "INSERT INTO lists (listId, listName, entry, ownerId, catId) VALUES (?,?,?,?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				list.setId(rs.getInt("maxid") + 1);
			}
			stmt = con.prepareStatement(insert);

			stmt.setInt(1, list.getId());
			stmt.setString(2, list.getListName());
			stmt.setString(3, list.getLists());
			stmt.setInt(4, list.getOwnerId());
			stmt.setInt(5, list.getCatId());

			stmt.executeUpdate();

		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return list;
	}

	public Lists update(Lists list) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String updateSQL = "UPDATE lists SET listName=?, catId=?, entry=? WHERE listId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, list.getListName());
			stmt.setInt(2, list.getCatId());
			stmt.setString(3, list.getLists());
			stmt.setInt(4, list.getId());

			stmt.executeUpdate();
			return list;

		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
	}

	public void delete(Lists list) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM lists " + "WHERE listId=" + list.getId());

		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
	}
	
	public Lists findById(int listId) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM lists WHERE listId=" + listId);

			// Ergebnis wird nur ein Objekt sein
			if (rs.next()) {
				Lists list = new Lists();
				list.setId(rs.getInt("listId"));
				list.setListName(rs.getString("listName"));
				list.setLists(rs.getString("entry"));
				list.setOwnerId(rs.getInt("ownerId"));
				list.setCatId(rs.getInt("catId"));
				return list;
			}
		} catch (SQLException e2) {

			throw new DatabaseException(e2);
		}
		return null;
	}
	
	public Vector<Lists> findAllLists() throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Lists> lists = new Vector<Lists>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM lists");

			// Fuer jede gefundene List wird ein neues Objekt erstellt
			while (rs.next()) {
				Lists l = new Lists();
				l.setId(rs.getInt("listId"));
				l.setListName(rs.getString("listName"));
				l.setLists(rs.getString("entry"));
				l.setOwnerId(rs.getInt("ownerId"));
				l.setCatId(rs.getInt("catId"));
				
				lists.addElement(l);
			}
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return lists;
	}
	
	public Vector<Lists> findAllListsByUserIdAndCatId(int ownerId, int catId) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen der Tupel aus der DB
		String selectByKey = "SELECT * FROM lists WHERE ownerId= " + ownerId + "AND catId= " + catId;

		Vector<Lists> lists = new Vector<Lists>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Lists l = new Lists();
				l.setId(rs.getInt("listId"));
				l.setListName(rs.getString("listName"));
				l.setLists(rs.getString("entry"));
				l.setOwnerId(rs.getInt("ownerId"));
				l.setCatId(rs.getInt("catId"));
				lists.addElement(l);
			}
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return lists;
	}
	

}
