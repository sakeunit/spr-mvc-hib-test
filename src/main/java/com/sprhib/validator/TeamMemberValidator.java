package com.sprhib.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sprhib.model.Member;

public class TeamMemberValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
				"teammember.firstname.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
				"teammember.lastname.required");
	}

}
