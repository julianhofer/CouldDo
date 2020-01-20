package de.hdm.itprojekt.coulddo.client.ui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.coulddo.client.ClientsideSettings;
import de.hdm.itprojekt.coulddo.client.CouldDoService;
import de.hdm.itprojekt.coulddo.client.CouldDoServiceAsync;
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
	private Category category = new Category();
	private CouldDoServiceAsync couldDoService = ClientsideSettings.getEditor();
	private Anchor logout = new Anchor();
	private FlowPanel buttons = new FlowPanel();
	private Label welcome = new Label("Wie es scheint bist du neu hier bei SCart, herzlich Wilkommen!");
	private Label infoLabel = new Label("Wähle einen Nutzernamen");
	private TextBox nameTextbox = new TextBox();
	private TextBox emailTextbox = new TextBox();
	private Button save = new Button("Registrieren");
	private Button logoutButton = new Button("Zurück zum Login");

	protected void onLoad() {
	}

	/**
	 * Konstruktor fuer die RegistryForm-Klasse.
	 * 
	 * @param logoutLink Link für den Logout
	 * @param email Attribut Email des Users
	 */
	public RegistryForm(String email) {
		user.setEmail(email);
		//logout.setHref(logoutLink);
		
		  welcome.setStyleName("h1"); infoLabel.setStyleName("text");
		  nameTextbox.setStyleName("textbox-big"); emailTextbox.setValue(email);
		  emailTextbox.setReadOnly(true); emailTextbox.setStyleName("textbox-big");
		  save.setStyleName("button"); logoutButton.setStyleName("button");
		  save.addClickHandler(saveClickHandler);
		  
		  logout.getElement().appendChild(logoutButton.getElement());
		  buttons.add(logout); buttons.add(save);
		  
		  this.setVerticalAlignment(ALIGN_TOP);
		  this.setHorizontalAlignment(ALIGN_CENTER); this.add(welcome);
		  this.add(infoLabel); this.add(nameTextbox); this.add(emailTextbox);
		  this.add(buttons);
		  
		  if (checkEmail(user.getEmail()) == false) {
		  Window.alert("E-Mail ist ungültig!"); } ;
		 
	}

	

	/**
	 * Methode zum anlegen eines Neuen Users in der DB
	 * 
	 * @param u (User-Objekt des aktuellen Users)
	 */
	public void saveUser(User u) {

		u.setUsername(nameTextbox.getValue());
		couldDoService.createUser(u, new AsyncCallback<User>() {

			public void onSuccess(User user) {
				couldDoService.getUserByEmail(user.getEmail(), userCallback);
			}

			public void onFailure(Throwable e) {
				Window.alert("Registrierung fehlgeschlagen " + e.getMessage());

			}
		});
	};

	public void createCategory(User u) {

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

		public void onSuccess(Category g) {
			category = g;
			Window.alert("Eine " + g + " wurde erfolgreich erstellt");
		}
	};

	/**
	 * Callback-Methode um eine neue Liste zu erstellen
	 * 
	 */
	AsyncCallback<Lists> listCallback = new AsyncCallback<Lists>() {

		public void onFailure(Throwable t) {
			Window.alert("Failed to make new GroceryList: " + t);
		}

		public void onSuccess(Lists l) {
			Window.Location.reload();
		}
	};


	AsyncCallback<User> userCallback = new AsyncCallback<User>() {

		public void onFailure(Throwable t) {
			Window.alert("Failed to get user object: " + t);
		}

		public void onSuccess(User u) {
			Category g = new Category("Neue Kategorie", u.getId());
			user = u;
			couldDoService.createCategory(g, createCategoryCallback);
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
				infoLabel.setText("Der Name muss aus 3 bis 10 Buchstaben bestehen");
			}
		}
	};

}