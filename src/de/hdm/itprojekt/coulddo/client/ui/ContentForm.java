package de.hdm.itprojekt.coulddo.client.ui;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.coulddo.client.ClientsideSettings;
import de.hdm.itprojekt.coulddo.client.CouldDoServiceAsync;
import de.hdm.itprojekt.coulddo.shared.bo.Category;
import de.hdm.itprojekt.coulddo.shared.bo.Entries;
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
	VerticalPanel scrollPanel = new VerticalPanel();
	VerticalPanel navigation = new VerticalPanel();
	HorizontalPanel outer = new HorizontalPanel();

	// Labels
	Label catLabel = new Label("CouldDo");

	public ContentForm(User u) {
		this.user = u;
		catTree.addSelectionHandler(selectionHandler);
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
		// scrollPanel.setWidget(navigation);

		this.add(navigation);

		getCats(user);
		GWT.log("angemeldeter User: " + user.getUsername() + " mit der Email " + user.getEmail());

	}

	public Vector<Category> getCats(User u) {
		
		couldDoService.getCategoryByUserId(u.getId(), new AsyncCallback<Vector<Category>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Kategorien konnten nicht von der DB geladen werden. Fehler: " + caught);

			}

			@Override
			public void onSuccess(Vector<Category> cat) {
				allCats.clear();
				allCats = cat;
				getNotes(user, allCats);
				GWT.log("Erste Kategorie des User: " + allCats.get(1).getCategoryName());
				getLists(user, allCats);
				
			}
		});
		
		return allCats;
	}

	public Vector<Notes> getNotes(User u, Vector<Category> allCats) {
		
		couldDoService.findAllNotesByUserIdAndCatVector(u.getId(), allCats, new AsyncCallback<Vector<Notes>>() {

			public void onFailure(Throwable caught) {
				Window.alert("Notizen konnten nicht von der DB geladen werden. Fehler: " + caught);
			}

			public void onSuccess(Vector<Notes> notes) {
				allNotes.clear();
				allNotes = notes;
				
			}
		});
		
		return allNotes;
	}

	public Vector<Lists> getLists(User u, Vector<Category> allCats) {

		couldDoService.findAllListsByUserIdAndCatVector(u.getId(), allCats, new AsyncCallback<Vector<Lists>>() {

			public void onFailure(Throwable caught) {
				Window.alert("Listen konnten nicht von der DB geladen werden. Fehler: " + caught);
			}

			public void onSuccess(Vector<Lists> lists) {
				allLists.clear();
				allLists = lists;
				fillTree();
				GWT.log("Liste: " + allLists.get(0).getListName());
			}
		});
		
		return allLists;
	}

	public void fillTree() {
		catTree.setAnimationEnabled(true);
		Label newCat = new Label("+ Kategorie hinzufügen");
		newCat.addStyleName("tree-new-cat");
		newCat.addClickHandler(createClickHandler);

		for (int i = 0; i < allCats.size(); i++) {
			Label catLabel = new Label(allCats.get(i).getCategoryName());
			catLabel.addStyleName("tree-cat");
			catLabel.addClickHandler(new CategoryClickHandler(allCats.get(i)));
			catTree.addItem(catLabel);

			for (int j = 0; j < allNotes.size(); j++) {
				 if (allCats.get(i).equals(getCatOfNote(allNotes.get(j)))) {
					Label noteLabel = new Label(allNotes.get(j).getNoteName());
					noteLabel.addClickHandler(new NoteClickHandler(allNotes.get(j), allCats.get(i)));
					noteLabel.addStyleName("tree");
					catTree.getItem(i).addItem(noteLabel);
					catTree.getItem(i).setState(true);
				}
			}
			Label newNote = new Label("+ Notiz hinzufügen");
			newNote.addStyleName("tree-new-entry");
			newNote.addClickHandler(new NewNoteClickHandler(allCats.get(i)));
			catTree.getItem(i).insertItem(0, newNote);

			for (int j = 0; j < allLists.size(); j++) {
				 if (allCats.get(i).equals(getCatOfList(allLists.get(j)))) {
					Label listLabel = new Label(allLists.get(j).getListName());
					listLabel.addClickHandler(new ListClickHandler(allLists.get(j), allCats.get(i)));
					listLabel.addStyleName("tree");
					catTree.getItem(i).addItem(listLabel);
					catTree.getItem(i).setState(true);
				 }

			}
			
			Label newList = new Label("+ Liste hinzufügen");
			newList.addStyleName("tree-new-entry");
			newList.addClickHandler(new NewListClickHandler(allCats.get(i)));
			catTree.getItem(i).insertItem(0, newList);
		
		}

		catTree.insertItem(0, newCat);
		loadingPanel.clear();
		catsPanel.add(catTree);
		navigation.add(catsPanel);
		if (RootPanel.get("navigation").getWidget(0).getStyleName() == "hide") {
		} else {
			RootPanel.get("content").getElement().getStyle().setProperty("margin",
					"120px 0px 0px " + (navigation.getOffsetWidth() + 30) + "px");
		}
	}

	SelectionHandler<TreeItem> selectionHandler = new SelectionHandler<TreeItem>() {

		public void onSelection(SelectionEvent<TreeItem> event) {
		}
	};

	public Category getCatOfNote(Notes note) {
		final Category noteCat = new Category();
		noteCat.setId(note.getCatId());
		noteCat.setCategoryName(note.getCategoryName());
		return noteCat;
	}

	public Category getCatOfList(Lists list) {
		final Category listCat = new Category();
		listCat.setId(list.getCatId());
		listCat.setCategoryName(list.getCatagoryName());
		return listCat;
	}

	class CategoryClickHandler implements ClickHandler {
		final Category selection;

		public CategoryClickHandler(Category cat) {
			selection = cat;
		}

		public void onClick(ClickEvent arg0) {
			 outer.clear();
			 outer.add(new EditCategoryForm(user, selection));
			 RootPanel.get("content").clear();
			 RootPanel.get("content").add(outer);
		}
	};

	class NoteClickHandler implements ClickHandler {
		final Notes selection;
		final Category category;

		public NoteClickHandler(Notes note, Category cat) {
			selection = note;
			category = cat;
		}

		public void onClick(ClickEvent arg0) {
			 outer.clear();
			 outer.add(new NoteForm(user, selection, category));
			 RootPanel.get("content").clear();
			 RootPanel.get("content").add(outer);
		}

	}

	class ListClickHandler implements ClickHandler {
		final Lists selection;
		final Category category;

		public ListClickHandler(Lists lists, Category cat) {
			selection = lists;
			category = cat;
		}

		public void onClick(ClickEvent arg0) {
			 outer.clear();
			 outer.add(new ListForm(user, selection, category));
			 RootPanel.get("content").clear();
			 RootPanel.get("content").add(outer);
		}

	}

	class NewNoteClickHandler implements ClickHandler {
		final Category newCat;

		public NewNoteClickHandler(Category cat) {
			newCat = cat;
		}

		public void onClick(ClickEvent arg0) {
			Notes newNote = new Notes();
			newNote.setNoteName("Neue Notiz");
			newNote.setOwnerId(user.getId());
			newNote.setCatId(newCat.getId());
			GWT.log("catId: " + newCat.getId());
			newNote.setCategoryName(newCat.getCategoryName());
			couldDoService.createNote(newNote, new AsyncCallback<Notes>() {

				public void onFailure(Throwable arg0) {

					Window.alert("Fehler: Neue Notiz konnte nicht erstellt werden");
				}

				public void onSuccess(Notes arg0) {
					Window.Location.reload();
//					outer.clear();
//					outer.add(new ContentForm(user));
//					RootPanel.get("navigation").clear();
//					RootPanel.get("navigation").add(outer);
			}

			});
			/*
			 * outer.clear(); outer.add(new ShoppingListForm(user, selection, group));
			 * RootPanel.get("content").clear(); RootPanel.get("content").add(outer);
			 */
		}

	}

	class NewListClickHandler implements ClickHandler {
		final Category category;

		public NewListClickHandler(Category cat) {
			category = cat;
		}

		public void onClick(ClickEvent arg0) {
			Lists newList = new Lists();
			Entries emptyEntry = new Entries();
//			emptyEntry.setListId(newList.getId());
			emptyEntry.setEntry("1. Eintrag");
			newList.setListName("Neue Liste");
			newList.setOwnerId(user.getId());
			newList.setCatId(category.getId());
			newList.setCategoryName(category.getCategoryName());
			couldDoService.createList(newList, emptyEntry, new AsyncCallback<Lists>() {

				public void onFailure(Throwable arg0) {

					Window.alert("Fehler: Neue Liste konnte nicht erstellt werden");
				}

				public void onSuccess(Lists arg0) {
					Window.Location.reload();//					outer.clear();
//					outer.add(new ContentForm(user));
//					RootPanel.get("navigation").clear();
//					RootPanel.get("navigation").add(outer);
				}

			});
			/*
			 * outer.clear(); outer.add(new ShoppingListForm(user, selection, group));
			 * RootPanel.get("content").clear(); RootPanel.get("content").add(outer);
			 */
		}

	}

	ClickHandler createClickHandler = new ClickHandler() {

		public void onClick(ClickEvent arg0) {
			outer.clear();
			CreateCategory createCategory = new CreateCategory(user);
			outer.add(createCategory);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(outer);

		}

	};

}
