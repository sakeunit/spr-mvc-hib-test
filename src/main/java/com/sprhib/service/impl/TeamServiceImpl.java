package com.sprhib.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.TeamDAO;
import com.sprhib.model.Team;
import com.sprhib.service.TeamService;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamDAO teamDAO;

	public TeamServiceImpl() {
		// nothing
	}

	public TeamServiceImpl(TeamDAO dao) {
		this.teamDAO = dao;
	}

	public void addTeam(Team team) {
		teamDAO.addTeam(team);
	}

	public void updateTeam(Team team) {
		teamDAO.updateTeam(team);
	}

	public Team getTeam(int id) {
		return teamDAO.getTeam(id);
	}

	public void deleteTeam(int id) {
		teamDAO.deleteTeam(id);
	}

	public List<Team> getTeams() {
		return teamDAO.getTeams();
	}

	@Override
	public Team getTeam(String name) {
		return teamDAO.getTeamByName(name);
	}

}
