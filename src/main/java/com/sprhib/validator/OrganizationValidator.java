package com.sprhib.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sprhib.model.Organization;

public class OrganizationValidator implements Validator {

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
		if (organization.getName().length() <  3) {
        	errors.rejectValue("name", "organization.name.notValid");
        }
		if (organization.getAddress().length() < 4) {
			errors.rejectValue("address", "organization.address.notValid");
		}
	}
}