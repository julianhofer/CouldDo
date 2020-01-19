package de.hdm.itprojekt.coulddo.server.db;

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

}
