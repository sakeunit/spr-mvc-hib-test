package com.sprhib.editor;

import java.beans.PropertyEditorSupport;

import com.sprhib.model.Member;
import com.sprhib.service.TeamMemberService;

public class TeamMemberEditorSupport extends PropertyEditorSupport {

	private TeamMemberService service;

	public TeamMemberEditorSupport(TeamMemberService service) {
		this.service = service;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text.matches("NONE")) {
			return;
		}
		Member member = service.getMember(Integer.parseInt(text));
		setValue(member);
	}
}