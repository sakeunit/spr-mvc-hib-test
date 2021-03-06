package com.sprhib.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sprhib.dao.TeamDAO;
import com.sprhib.model.Team;

@Repository
public class TeamDAOImpl implements TeamDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public TeamDAOImpl() {
	}
	
	public TeamDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void addTeam(Team team) {
		getCurrentSession().save(team);
	}

	public void updateTeam(Team team) {
		Team teamToUpdate = getTeam(team.getId());
		teamToUpdate.setName(team.getName());
		teamToUpdate.setRating(team.getRating());
		teamToUpdate.setOrganization(team.getOrganization());
		getCurrentSession().update(teamToUpdate);
	}

	public Team getTeam(int id) {
		Team team = (Team) getCurrentSession().get(Team.class, id);
		return team;
	}

	public void deleteTeam(int id) {
		Team team = getTeam(id);
		if (team != null)
			getCurrentSession().delete(team);
	}

	@Override
	public List<Team> getTeams() {
		return getCurrentSession().createQuery("from Team").list();
	}

	@Override
	public Team getTeamByName(String name) {
		return (Team) getCurrentSession()
				.createQuery("from Team where name=:n").setParameter("n", name)
				.uniqueResult();
	}

}
