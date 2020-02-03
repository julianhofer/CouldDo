package de.hdm.itprojekt.coulddo.server;

import java.util.logging.Logger;

import de.hdm.itprojekt.coulddo.client.ClientsideSettings;


/**
 * Logger für serverseitige aktionen
 */
public class ServersideSettings extends ClientsideSettings {

	private static final String LOGGER_NAME = "Logger";
	private static final Logger logger = Logger.getLogger(LOGGER_NAME);;

	public static Logger getLogger() {

		return logger;
	}

}
