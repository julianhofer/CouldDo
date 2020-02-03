package de.hdm.itprojekt.coulddo.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.coulddo.client.ClientsideSettings;
import de.hdm.itprojekt.coulddo.client.CouldDoServiceAsync;
import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.User;

public class CreateCategory extends VerticalPanel {

	private CouldDoServiceAsync couldDoService = ClientsideSettings.getEditor();

	public CreateCategory() {

	}

	public CreateCategory(User u) {

		this.user = u;

	}

	User user = null;

	TextBox catTextBox = new TextBox();

	Label nameLabel = new Label();
	Label responseLabel = new Label();

	Button createCatButton = new Button("Erstellen");
	Button backButton = new Button("Zurück");

	VerticalPanel contentBox = new VerticalPanel();

	HorizontalPanel createCatPanel = new HorizontalPanel();
	HorizontalPanel inputPanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	HorizontalPanel responsePanel = new HorizontalPanel();

	public void onLoad() {
		super.onLoad();

		Label createCatLabel = new Label("Neue Kategorie erstellen");

		createCatLabel.addStyleName("h1");
		backButton.addStyleName("button");
		createCatButton.addStyleName("button");
		nameLabel.setStyleName("text");
		responseLabel.setStyleName("text");
		catTextBox.setStyleName("textbox");

		createCatLabel.setHorizontalAlignment(ALIGN_CENTER);
		createCatPanel.add(createCatLabel);
		createCatPanel.setHorizontalAlignment(ALIGN_CENTER);

		inputPanel.add(nameLabel);
		inputPanel.add(catTextBox);
		inputPanel.setHorizontalAlignment(ALIGN_CENTER);

		buttonPanel.add(backButton);
		buttonPanel.add(createCatButton);
		buttonPanel.setHorizontalAlignment(ALIGN_CENTER);

		responseLabel.setHorizontalAlignment(ALIGN_CENTER);
		responsePanel.add(responseLabel);
		responseLabel.setVisible(false);
		responsePanel.setHorizontalAlignment(ALIGN_CENTER);

		contentBox.add(createCatPanel);
		contentBox.add(inputPanel);
		contentBox.add(buttonPanel);
		contentBox.add(responsePanel);

		this.add(contentBox);
		// this.add(footer);

		// CLICKHANDLER FOR CREATE GROUP-BUTTON=============================
		createCatButton.addClickHandler(new CreateCatClickHandler());

		backButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// RootPanel.get("content").clear();
				Window.Location.replace("/CouldDo.html");
			}
		});

	}

	class CreateCatClickHandler implements ClickHandler {
		/*
		 * Wird aufgerufen sobald der User auf den createGroup-Button klickt
		 */
		public void onClick(ClickEvent event) {
			// Uebergabe des Gruppennamen an den Server/Mapper (s. Methode)

			if (checkName(catTextBox.getText()) != null) {
				String cat = catTextBox.getText();
				createCategory(cat);

			} else {
				responseLabel.setVisible(true);
				responseLabel.setText("Fehler: Bitte geben Sie einen passenden Namen ein");
			}
		}
	}

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

	public void createCategory(String catName) {

		Category createCat = new Category();
		createCat.setCategoryName(catName);
		responseLabel.setVisible(true);
		responseLabel.setText("");

		couldDoService.createCategory(createCat, new AsyncCallback<Category>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Es konnte keine neue Kategorie erstellt werden. Fehler: " + caught);
			}

			@Override
			public void onSuccess(Category cat) {
				Category tempCat = cat;
				tempCat.setOwnerId(user.getId());
				couldDoService.saveCategory(tempCat, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Die neue Kategorie konnte nicht dem User " + user.getUsername()
								+ " zugeordnet werden. Fehler: " + caught);
					}

					@Override
					public void onSuccess(Void result) {
						Window.Location.replace("/CouldDo.html");

					}

				});

			}

		});

	}

}
