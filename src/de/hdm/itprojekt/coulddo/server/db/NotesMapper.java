package de.hdm.itprojekt.coulddo.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itprojekt.coulddo.shared.DatabaseException;
import de.hdm.itprojekt.coulddo.shared.bo.Notes;

public class NotesMapper {

	private static NotesMapper notesMapper = null;

	/**
	 * Beugt mehrfach Instanzierung vor.
	 */
	protected NotesMapper() {
	};

	public static NotesMapper notesMapper() {
		if (notesMapper == null) {
			notesMapper = new NotesMapper();
		}
		return notesMapper;
	}
	
	public Notes insert(Notes note) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String maxId = "SELECT MAX(noteId) AS maxid FROM notes";

		String insert = "INSERT INTO notes (noteId, noteName, notes, ownerId, catId, categoryName) VALUES (?,?,?,?,?,?)";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(maxId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				note.setId(rs.getInt("maxid") + 1);
			}
			stmt = con.prepareStatement(insert);

			stmt.setInt(1, note.getId());
			stmt.setString(2, note.getNoteName());
			stmt.setString(3, note.getNotes());
			stmt.setInt(4, note.getOwnerId());
			stmt.setInt(5, note.getCatId());
			stmt.setString(6, note.getCategoryName());

			stmt.executeUpdate();

		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return note;
	}

	public Notes update(Notes note) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		String updateSQL = "UPDATE notes SET noteName=?, catId=?, notes=? WHERE noteId=?";

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(updateSQL);

			stmt.setString(1, note.getNoteName());
			stmt.setInt(2, note.getCatId());
			stmt.setString(3, note.getNotes());
			stmt.setInt(4, note.getId());

			stmt.executeUpdate();
			return note;

		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
	}

	public void delete(Notes note) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM notes " + "WHERE noteId=" + note.getId());

		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
	}
	
	public Notes findById(int noteId) throws DatabaseException {
		Connection con = DBConnection.connection();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM notes WHERE noteId=" + noteId);

			// Ergebnis wird nur ein Objekt sein
			if (rs.next()) {
				Notes note = new Notes();
				note.setId(rs.getInt("noteId"));
				note.setNoteName(rs.getString("noteName"));
				note.setNotes(rs.getString("notes"));
				note.setOwnerId(rs.getInt("ownerId"));
				note.setCatId(rs.getInt("catId"));
				return note;
			}
		} catch (SQLException e2) {

			throw new DatabaseException(e2);
		}
		return null;
	}
	
	public Vector<Notes> findAllNotes() throws DatabaseException {
		Connection con = DBConnection.connection();

		Vector<Notes> notes = new Vector<Notes>();

		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM notes");

			// Fuer jede gefundene note wird ein neues Objekt erstellt
			while (rs.next()) {
				Notes n = new Notes();
				n.setId(rs.getInt("noteId"));
				n.setNoteName(rs.getString("noteName"));
				n.setNotes(rs.getString("notes"));
				n.setOwnerId(rs.getInt("ownerId"));
				n.setCatId(rs.getInt("catId"));
				
				notes.addElement(n);
			}
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return notes;
	}
	
	public Vector<Notes> findAllNotesByUserIdAndCatId(int ownerId, int catId) throws DatabaseException {
		Connection con = null;
		PreparedStatement stmt = null;

		// SQL-Anweisung zum auslesen der Tupel aus der DB
		String selectByKey = "SELECT * FROM notes WHERE ownerId= " + ownerId + " AND catId= " + catId;

		Vector<Notes> notes = new Vector<Notes>();

		try {
			con = DBConnection.connection();
			stmt = con.prepareStatement(selectByKey);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Notes n = new Notes();
				n.setId(rs.getInt("noteId"));
				n.setNoteName(rs.getString("noteName"));
				n.setNotes(rs.getString("notes"));
				n.setOwnerId(rs.getInt("ownerId"));
				n.setCatId(rs.getInt("catId"));
				notes.addElement(n);
			}
		} catch (SQLException e2) {
			throw new DatabaseException(e2);
		}
		return notes;
	}
	
	
}
