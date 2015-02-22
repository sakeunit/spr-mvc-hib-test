package com.sprhib.editor;

import java.beans.PropertyEditorSupport;

import com.sprhib.model.Organization;
import com.sprhib.service.OrganizationService;

public class OrganizationEditorSupport extends PropertyEditorSupport {

	private OrganizationService service;

	public OrganizationEditorSupport(OrganizationService service) {
		this.service = service;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text.matches("NONE")) {
			return;
		}
		Organization organization = service.getOrganization(Integer
				.parseInt(text));
		setValue(organization);
	}
	
}
