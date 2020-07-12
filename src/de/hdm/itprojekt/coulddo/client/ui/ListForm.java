package de.hdm.itprojekt.coulddo.client.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.coulddo.client.ClientsideSettings;
import de.hdm.itprojekt.coulddo.client.CouldDoServiceAsync;

import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.Entries;
import de.hdm.itprojekt.coulddo.shared.bo.Lists;
import de.hdm.itprojekt.coulddo.shared.bo.User;

public class ListForm extends VerticalPanel {

	CouldDoServiceAsync couldDoService = ClientsideSettings.getEditor();

	public ListForm() {

	}

	public ListForm(User u, Lists l, Category c) {
		this.user = u;
		this.list = l;
		this.cat = c;
	}

	User user = new User();
	Lists list = new Lists();
	Category cat = new Category();
	

	TextBox listNameTextBox = new TextBox();
	TextBox addItemBox = new TextBox();
	Label itemLabel = new Label();
	
	Button saveList = new Button("Speichern");
	Button deleteList = new Button("Entfernen");
	Button back = new Button("ZurÃ¼ck");
	Button deleteItem = new Button("-");
	Button add = new Button("+");
	Button edit = new Button("edit");
	
	FlexTable listTable = new FlexTable();
	VerticalPanel contentPanel = new VerticalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	HorizontalPanel tableButtons = new HorizontalPanel();
	VerticalPanel inputPanel = new VerticalPanel();

	public void onLoad() {
		super.onLoad();

		listNameTextBox.setText(list.getListName());
		listNameTextBox.addStyleName("textbox-big");
		// itemLabel.setText(list.getLists());
		itemLabel.addStyleName("textbox");		
		saveList.addStyleName("button");
		deleteList.addStyleName("button");
		back.addStyleName("button");
		add.addStyleName("table-icon-button");
		edit.addStyleName("table-icon-button");
		deleteItem.addStyleName("table-icon-button");
		// ToDo Table Style
		listTable.setStyleName("table");
		
		tableButtons.add(deleteItem);
		tableButtons.add(edit);
		
		buttonPanel.add(back);
		buttonPanel.add(deleteList);
		buttonPanel.add(saveList);

		listTable.setText(0, 0, "Eintrag");
		listTable.setCellPadding(0);
		
		inputPanel.add(listNameTextBox);
		inputPanel.add(listTable);
		contentPanel.add(inputPanel);
		contentPanel.add(buttonPanel);

		this.add(contentPanel);
		
		updateTable(user, cat, list);
		
		saveList.addClickHandler(new SaveListClickHandler(user, list, cat));
		deleteList.addClickHandler(new DeleteListClickHandler(list));
		back.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// RootPanel.get("content").clear();
				Window.Location.replace("/CouldDo.html");
			}
		});

	}
	
	
	public void updateTable(User user, Category cat, Lists list) {
		

		add.addClickHandler(new AddEntryClickHandler(addItemBox.getText()));
		
//		for (int i = 0; i < entries.(); i++) {
//			GWT.log("lists " + list.elementAt(i).getLists());
//			edit.addClickHandler(new EditEntryClickHandler(list.elementAt(i)));
			
//			listTable.setText(1, 0, list.getLists());
			listTable.setWidget(1, 1, tableButtons);
			listTable.setWidget(2, 0, addItemBox);
			listTable.setWidget(2, 1, add);
//			i++;
			 
//		}
		
		GWT.log("cat " + cat.getCategoryName());
		GWT.log("user " + user.getUsername());
		
		
	}
	
	class EditEntryClickHandler implements ClickHandler {

		public EditEntryClickHandler(Lists list) {
			
		}
		
		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class AddEntryClickHandler implements ClickHandler {

		Lists newList = new Lists();
		ArrayList<String> entries = new ArrayList<String>();
		
		public AddEntryClickHandler(String entry) {
			entries.add(entries.size(), entry);
		}
		
		@Override
		public void onClick(ClickEvent event) {
//			couldDoService.saveList(newList, new AsyncCallback<Lists>() {
				
			}
			
		}
		
	

	class SaveListClickHandler implements ClickHandler {
		Lists list = new Lists();
		User user = new User();
		Category cat = new Category();
		Entries entry = new Entries();

		public SaveListClickHandler(User u, Lists list, Category c) {
			this.list = list;
			this.user = u;
			this.cat = c;
		}

		public void onClick(ClickEvent event) {
			String newListName = listNameTextBox.getText();
			String newList = itemLabel.getText();
			list.setListName(newListName);
			entry.setEntry(newList);

			couldDoService.saveList(list, new AsyncCallback<Lists>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Die Liste konnte nicht gespeichert werden. Fehler: " + caught);

				}

				@Override
				public void onSuccess(Lists result) {
					Lists newList = new Lists();
					newList = result;
					inputPanel.clear();
					inputPanel.add(new ListForm(user, newList, cat));
					RootPanel.get("content").clear();
					RootPanel.get("content").add(inputPanel);

				}

			});

		}

	}

	class DeleteListClickHandler implements ClickHandler {
		Lists list = new Lists();

		public DeleteListClickHandler(Lists l) {
			this.list = l;
			;
		}

		@Override
		public void onClick(ClickEvent event) {
			couldDoService.deleteList(list, new AsyncCallback<Lists>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Der Eintrag konnte nicht gelöscht werden. Fehler: " + caught);

				}

				@Override
				public void onSuccess(Lists result) {
					Window.Location.replace("/CouldDo.html");

				}

			});

		}
	}

	
}