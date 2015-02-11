package com.sprhib.editor;

import java.beans.PropertyEditorSupport;

import com.sprhib.model.Team;
import com.sprhib.service.TeamService;

public class TeamEditorSupport extends PropertyEditorSupport {

	private TeamService teamService;

	public TeamEditorSupport(TeamService teamService) {
		this.teamService = teamService;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text.matches("NONE")) {
			return;
		}
		Team team = teamService.getTeam(Integer.parseInt(text));
		setValue(team);
	}
}