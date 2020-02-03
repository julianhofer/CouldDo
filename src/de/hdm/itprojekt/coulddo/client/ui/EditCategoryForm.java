package de.hdm.itprojekt.coulddo.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.coulddo.client.ClientsideSettings;
import de.hdm.itprojekt.coulddo.client.CouldDoServiceAsync;
import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.User;

public class EditCategoryForm extends VerticalPanel{
	
	private CouldDoServiceAsync couldDoService = ClientsideSettings.getEditor();
	
	public EditCategoryForm() {
		
	}
	
	public EditCategoryForm(User u, Category c) {
		this.user = u;
		this.cat = c;
	}
	
	User user = new User();
	Category cat = new Category();
	
	TextBox catTextBox = new TextBox();
	Label catName = new Label("Kategoriename:");
	
	Button saveCat = new Button("Speichern");
	Button deleteCat = new Button("Entfernen");
	Button back = new Button("ZurÃ¼ck");
	
	VerticalPanel contentBox = new VerticalPanel();

	HorizontalPanel inputPanel = new HorizontalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	
	
	public void onLoad() {
		super.onLoad();
		
		catTextBox.setText(cat.getCategoryName());
		catTextBox.addStyleName("textbox-big");
		catName.addStyleName("h2");
		saveCat.addStyleName("button");
		deleteCat.addStyleName("button");
		back.addStyleName("button");
		
		catName.setHorizontalAlignment(ALIGN_LEFT);
		
		inputPanel.add(catName);
		inputPanel.add(catTextBox);
		inputPanel.setHorizontalAlignment(ALIGN_CENTER);
		
		buttonPanel.add(back);
		buttonPanel.add(deleteCat);
		buttonPanel.add(saveCat);
		
		contentBox.add(inputPanel);
		contentBox.add(buttonPanel);
		
		this.add(contentBox);
		
		saveCat.addClickHandler(new SaveCatClickHandler(cat));
		deleteCat.addClickHandler(new DeleteCatClickHandler(cat));		
		back.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// RootPanel.get("content").clear();
				Window.Location.replace("/CouldDo.html");
			}
		});
		
	}

	class SaveCatClickHandler implements ClickHandler {
		Category cat = new Category();
		
		public SaveCatClickHandler(Category c) {
			this.cat = c;
		}
		
		public void onClick(ClickEvent event) {
			String newGroupName = catTextBox.getText();
			cat.setCategoryName(newGroupName);
			
			couldDoService.saveCategory(cat, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(newGroupName + " konnte nicht als neuer Name gespeichert werden. Fehler: " + caught);
					
				}

				@Override
				public void onSuccess(Void result) {
					Window.Location.replace("/CouldDo.html");			
				}
				
			});
			
		}
		
	}
	
	class DeleteCatClickHandler implements ClickHandler {
		Category cat = new Category();
		
		public DeleteCatClickHandler(Category c) {
			this.cat = c;
		}
		@Override
		public void onClick(ClickEvent event) {
			couldDoService.deleteCategory(cat, new AsyncCallback<Void>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Die Kategorie konnte nicht gelöscht werden. Fehler: " + caught);
					
				}

				@Override
				public void onSuccess(Void result) {
					Window.Location.replace("/CouldDo.html");
					
				}
				
			});
			
		}
		
	}
	
	
	
	
	
}
