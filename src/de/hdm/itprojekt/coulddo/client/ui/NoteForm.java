package de.hdm.itprojekt.coulddo.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.coulddo.client.ClientsideSettings;
import de.hdm.itprojekt.coulddo.client.CouldDoServiceAsync;
import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.Notes;
import de.hdm.itprojekt.coulddo.shared.bo.User;

public class NoteForm extends VerticalPanel {
	
	CouldDoServiceAsync couldDoService = ClientsideSettings.getEditor();
	
	public NoteForm() {
		
	}
	
	public NoteForm(User u, Notes n, Category c) {
		this.user = u;
		this.note = n;
		this.cat = c;
	}
	
	User user = new User();
	Notes note = new Notes();
	Category cat = new Category();
	
	TextBox noteNameTextBox = new TextBox();
	TextArea noteTextBox = new TextArea();
	
	Button saveNote = new Button("Speichern");
	Button deleteNote = new Button("Entfernen");
	Button back = new Button("ZurÃ¼ck");
	
	VerticalPanel contentBox = new VerticalPanel();

	VerticalPanel inputPanel = new VerticalPanel();
	HorizontalPanel buttonPanel = new HorizontalPanel();
	
	
	
	public void onLoad() {
		super.onLoad();
		
		noteNameTextBox.setText(note.getNoteName());
		noteNameTextBox.addStyleName("textbox-big");
		noteTextBox.setText(note.getNotes());
		noteTextBox.addStyleName("notebox-big");
		saveNote.addStyleName("button");
		deleteNote.addStyleName("button");
		back.addStyleName("button");
		
		inputPanel.setHorizontalAlignment(ALIGN_CENTER);
		inputPanel.addStyleName("inner-content");
		inputPanel.add(noteNameTextBox);
		inputPanel.add(noteTextBox);
		inputPanel.add(buttonPanel);
		
		
		buttonPanel.add(back);
		buttonPanel.add(deleteNote);
		buttonPanel.add(saveNote);
		
		contentBox.add(inputPanel);
		
		
		this.add(contentBox);
		
		saveNote.addClickHandler(new SaveNoteClickHandler(user, note, cat));
		deleteNote.addClickHandler(new DeleteNoteClickHandler(note));		
		back.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// RootPanel.get("content").clear();
				Window.Location.replace("/CouldDo.html");
			}
		});
		
	}
	
	class SaveNoteClickHandler implements ClickHandler {
		Notes note = new Notes();
		User user = new User();
		Category cat = new Category();
		
		public SaveNoteClickHandler(User u, Notes n, Category c) {
			this.note = n;
			this.user = u;
			this.cat = c;
		}
		
		public void onClick(ClickEvent event) {
			String newNoteName = noteNameTextBox.getText();
			String newNote = noteTextBox.getText();
			note.setNoteName(newNoteName);
			note.setNotes(newNote);
			
			couldDoService.saveNote(note, new AsyncCallback<Notes>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Die Notiz konnte nicht gespeichert werden. Fehler: " + caught);
					
				}
				@Override
				public void onSuccess(Notes result) {
					ContentForm contentForm = new ContentForm(user);
					inputPanel.clear();
					inputPanel.add(new NoteForm(user, result, cat));
					RootPanel.get("navigation").clear();
					RootPanel.get("content").clear();
					RootPanel.get("content").add(inputPanel);
					RootPanel.get("navigation").add(contentForm);
					
				}
				
			});
			
		}
		
	}
	
	
	class DeleteNoteClickHandler implements ClickHandler {
		Notes note = new Notes();
		
		public DeleteNoteClickHandler(Notes n) {
			this.note = n;
		}
		@Override
		public void onClick(ClickEvent event) {
			couldDoService.deleteNote(note, new AsyncCallback<Notes>(){

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Die Notiz konnte nicht gelöscht werden. Fehler: " + caught);
					
				}
				@Override
				public void onSuccess(Notes result) {
					Window.Location.replace("/CouldDo.html");
					
				}
				
			});
			
		}
	}
	
	
	
	
	
}
	
	


