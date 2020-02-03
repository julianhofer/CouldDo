package de.hdm.itprojekt.coulddo.client.ui;

import java.util.Vector;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.coulddo.client.ClientsideSettings;
import de.hdm.itprojekt.coulddo.client.CouldDoService;
import de.hdm.itprojekt.coulddo.client.CouldDoServiceAsync;
import de.hdm.itprojekt.coulddo.client.ui.ContentForm;
import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.Lists;
import de.hdm.itprojekt.coulddo.shared.bo.User;

/**
 * Die RegistryForm wird zum Login aufgerufen, wenn der User die Applikation zum
 * ersten mal verwendet.
 * 
 * @author PatrickLehle, Marco Dell'Oso
 */
public class RegistryForm extends VerticalPanel {

	private User user = new User();
	private Category cat = new Category();
	private ScrollPanel navigationPanel = new ScrollPanel();
	private HorizontalPanel contentPanel = new HorizontalPanel();
	private ContentForm contentForm;
	private CouldDoServiceAsync couldDoService = ClientsideSettings.getEditor();
	private FlowPanel buttons = new FlowPanel();
	private Label welcome = new Label("Hier kannst du dich anmelden");
	private Label nameLabel = new Label("Dein Nutzername");
	private Label emailLabel = new Label("Deine Email");
	private TextBox nameTextbox = new TextBox();
	private TextBox emailTextbox = new TextBox();
	private Button save = new Button("Anmelden");

	protected void onLoad() {
	}

	/**
	 * Konstruktor fuer die RegistryForm-Klasse.
	 * 
	 * @param email Attribut Email des Users
	 */
	public RegistryForm() {

		welcome.setStyleName("h1");
		nameLabel.setStyleName("text");
		nameTextbox.setStyleName("textbox-big");
		emailLabel.setStyleName("text");
		emailTextbox.setStyleName("textbox-big");
		save.setStyleName("button");
		save.addClickHandler(saveClickHandler);
		buttons.add(save);

		this.setVerticalAlignment(ALIGN_TOP);
		this.setHorizontalAlignment(ALIGN_CENTER);
		this.add(welcome);
		this.add(nameLabel);
		this.add(nameTextbox);
		this.add(emailLabel);
		this.add(emailTextbox);
		this.add(buttons);

	}

	/**
	 * Methode zum anlegen eines Neuen Users in der DB
	 * 
	 * @param u (User-Objekt des aktuellen Users)
	 */
	public void saveUser(User u) {

		u.setUsername(nameTextbox.getValue());
		u.setEmail(emailTextbox.getValue());

		couldDoService.getUserByEmail(u.getEmail(), new AsyncCallback<User>() {

			public void onFailure(Throwable caught) {

				if (checkEmail(user.getEmail()) == false) {
					Window.alert("E-Mail ist ungültig!");
				}

				couldDoService.createUser(u, new AsyncCallback<User>() {

					public void onSuccess(User user) {
						Category c = new Category("Neue Kategorie", user.getId());
						couldDoService.createCategory(c, createCategoryCallback);
						loadPage(user);
					}

					public void onFailure(Throwable e) {
						Window.alert("Registrierung fehlgeschlagen " + e.getMessage());

					}
				});

			}

			public void onSuccess(User user) {
				loadPage(user);
				
				Window.alert("Du wirst mit deiner bestehenden Email Adresse angemeldet");

			}

		});

	};
	
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

	/**
	 * Ueberprueft korrekte syntax der Eingabe
	 * 
	 * @param str
	 * @return true, wenn der Name passt
	 */
	private Boolean checkName(String str) {
		// \w -> nur Buchstaben oder Zahlen
		if (str.matches("^[a-zA-Z]*$") == false) {
			return false;
		} else if (str.length() > 10) {
			return false;
		} else if (str.length() < 2) {
			return false;
		} else {
			return true;
		}
	};

	/**
	 * Methode um die Eingabe der E-Mail zu ueberpruefen
	 * 
	 * @param email mail zu pruefende email
	 * @return true, wenn die E-Mail passt / false, wenn der Syntax nicht stimmt
	 */
	private Boolean checkEmail(String email) {
		if (!email.matches("^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Callback-Methode um eine neue Gruppe zu erstellen. Bei Erfolg wird der (neue)
	 * User der Gruppe hinzugefuegt.
	 */
	AsyncCallback<Category> createCategoryCallback = new AsyncCallback<Category>() {

		public void onFailure(Throwable t) {
			Window.alert("Failed to create new Category: " + t);
		}

		public void onSuccess(Category c) {
			cat = c;
			Window.alert("Eine " + c.getCategoryName() + " wurde erfolgreich erstellt");
		}
	};

	/**
	 * ClickHandler fuer den "Registrieren"-Button Die Methode checkt den Sntax des
	 * Usernamen und startet eine Callback um den User neun in der Db anzulegen.
	 */
	ClickHandler saveClickHandler = new ClickHandler() {

		public void onClick(ClickEvent e) {
			if (checkName(nameTextbox.getValue())) {
				saveUser(user);
			} else {
				nameLabel.setText("Der Name muss aus 3 bis 10 Buchstaben bestehen");
			}
		}
	};

}