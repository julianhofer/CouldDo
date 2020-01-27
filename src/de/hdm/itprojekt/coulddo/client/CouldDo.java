package de.hdm.itprojekt.coulddo.client;

import de.hdm.itprojekt.coulddo.client.ui.ContentForm;
import de.hdm.itprojekt.coulddo.client.ui.FooterForm;
import de.hdm.itprojekt.coulddo.client.ui.HeaderForm;
import de.hdm.itprojekt.coulddo.client.ui.RegistryForm;
import de.hdm.itprojekt.coulddo.shared.FieldVerifier;
import de.hdm.itprojekt.coulddo.shared.bo.User;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CouldDo implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final CouldDoServiceAsync couldDoService = GWT.create(CouldDoService.class);
	private ScrollPanel navigationPanel = new ScrollPanel();
	private HorizontalPanel contentPanel = new HorizontalPanel();
	User user = new User();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		user.setUsername("Juls");
		user.setEmail("juls@test.de");
		// RegistryForm registryForm = new RegistryForm();
 		// RootPanel.get("content").clear();
 		// RootPanel.get("content").add(registryForm);
 		loadPage(user);
 		FooterForm footer = new FooterForm();
 		RootPanel.get("footer").clear();
		RootPanel.get("footer").add(footer);


}
	
	public void loadPage(User user) {		
		
		HeaderForm header = new HeaderForm();
		ContentForm contentForm = new ContentForm(user);
		contentForm.addStyleName("navigation");
		contentForm.setHeight("100%");
		navigationPanel.add(contentForm);
		
		RootPanel.get("header").clear();
		RootPanel.get("header").add(header);
		
		RootPanel.get("navigation").clear();
		RootPanel.get("navigation").add(navigationPanel);
		RootPanel.get("content").clear();
		RootPanel.get("content").add(contentPanel);
		
	}
	
	
	
	
}