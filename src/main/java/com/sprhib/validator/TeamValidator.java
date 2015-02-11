package com.sprhib.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sprhib.model.Team;

public class TeamValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Team.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "team.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "rating", "team.rating.empty");
        Team team = (Team) target;
        if (team.getOrganization() == null || team.getOrganization().getId() == 0) {
        	errors.rejectValue("organization", "team.organization.empty");
        }
        if (team.getRating() == null || team.getRating() < 0 || team.getRating() > 10) {
        	errors.rejectValue("rating", "team.rating.notValid");
        }
	}

}
