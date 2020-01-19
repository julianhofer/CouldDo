package de.hdm.itprojekt.coulddo.server.db;

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
}
