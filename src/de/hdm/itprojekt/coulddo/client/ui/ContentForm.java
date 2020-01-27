package de.hdm.itprojekt.coulddo.client.ui;

import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.coulddo.client.ClientsideSettings;
import de.hdm.itprojekt.coulddo.client.CouldDoServiceAsync;
import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.Lists;
import de.hdm.itprojekt.coulddo.shared.bo.Notes;
import de.hdm.itprojekt.coulddo.shared.bo.User;


public class ContentForm extends VerticalPanel {

	
	CouldDoServiceAsync couldDoService = ClientsideSettings.getEditor();
	
	User user = new User();
	Vector<Category> allCats = new Vector<Category>();
	Vector<Notes> allNotes = new Vector<Notes>();
	Vector<Lists> allLists = new Vector<Lists>();
	
	// Panles
	private Tree catTree = new Tree();
	VerticalPanel catsPanel = new VerticalPanel();
	VerticalPanel catBtnPanel = new VerticalPanel();
	HorizontalPanel loadingPanel = new HorizontalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	VerticalPanel navigation = new VerticalPanel();
	HorizontalPanel outer = new HorizontalPanel();

	// Labels
	Label catLabel = new Label("Kategorien");
	
	public ContentForm(User u) {
		this.user = u;
		// categoryTree.addSelectionHandler(selectionHandler);
	}
	
	
	public void onLoad() {
		navigation.clear();
		catsPanel.clear();

		outer.addStyleName("inner-content");

		catsPanel.setHorizontalAlignment(ALIGN_LEFT);
		catLabel.addStyleName("h1");
		catLabel.setHorizontalAlignment(ALIGN_CENTER);

		navigation.setHorizontalAlignment(ALIGN_LEFT);
		navigation.setVerticalAlignment(ALIGN_TOP);
		navigation.add(catLabel);
		navigation.add(loadingPanel);
		scrollPanel.setWidget(navigation);

		this.add(scrollPanel);
		
		getCats(user);
		getNotes(user);
		getLists(user);
		
	}
	



	public Vector<Category> getCats(User u){
		
		couldDoService.getCategoryByUserId(u.getId(), new AsyncCallback<Vector<Category>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Kategorien konnten nicht von der DB geladen weren. Fehler: " + caught);
				
			}

			@Override
			public void onSuccess(Vector<Category> cat) {
				allCats.clear();
				allCats = cat;
				
			}
			
		});;
		
		
		return allCats;	
	}
	
	
	
	public Vector<Notes> getNotes(User u) {
		
		
		
		return allNotes;	
	}
	
	public Vector<Lists> getLists(User u) {
		
		
		return allLists;
	}
	
	
	
}
