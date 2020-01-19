package de.hdm.itprojekt.coulddo.server.db;

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

}
