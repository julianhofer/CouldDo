package de.hdm.itprojekt.coulddo.client.ui;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class FooterForm extends HorizontalPanel {

	public FooterForm() {
		Label txt = new Label("IT Projekt WS19 Julian Hofer © Copyright 2020");


		this.setHorizontalAlignment(ALIGN_CENTER);
		this.setVerticalAlignment(ALIGN_MIDDLE);
		this.addStyleName("footer");
		this.add(txt);
		
	}
}

	

