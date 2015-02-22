package com.sprhib.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sprhib.model.Member;
import com.sprhib.service.TeamMemberService;

public class TeamMemberValidator implements Validator {

	private TeamMemberService teamMemberService;

	public TeamMemberValidator(TeamMemberService teamMemberService) {
		this.teamMemberService = teamMemberService;
	}

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
		Member member = (Member) target;
		if (member == null) {
			errors.reject("member.notValid");
		}
		if (errors.hasErrors()) {
			return;
		}

		Member memberInDb = teamMemberService.getMember(member.getLastName(),
				member.getFirstName());
		if (memberInDb != null) {
			errors.reject("member.exists");
			return;
		}
	}

}
