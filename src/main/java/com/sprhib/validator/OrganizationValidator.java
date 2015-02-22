package com.sprhib.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sprhib.model.Organization;
import com.sprhib.service.OrganizationService;

public class OrganizationValidator implements Validator {

	private OrganizationService organizationService;

	public OrganizationValidator(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Override
	public boolean supports(Class clazz) {
		return Organization.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				"organization.name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address",
				"organization.address.required");

		Organization organization = (Organization) target;
		if (organization == null) {
			errors.reject("organization.notValid");
		}
		if (organization.getName() == null
				|| organization.getName().length() < 3) {
			errors.rejectValue("name", "organization.name.notValid");
		}
		if (organization.getAddress() == null
				|| organization.getAddress().length() < 4) {
			errors.rejectValue("address", "organization.address.notValid");
		}
		if (errors.hasErrors()) {
			return;
		}
		Organization orgInDb = organizationService.getOrganization(organization
				.getName());
		if (orgInDb != null) {
			errors.reject("organization.exists");
			return;
		}
	}
}