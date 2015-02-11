package com.sprhib.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import com.sprhib.model.Organization;
import com.sprhib.model.Team;

public class TeamDAOTest extends BaseDAOTestConfig {

	@Test
	public void testAddTeam() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Organization organization = new Organization();
		organization.setName("organizationname0");
		organization.setAddress("organizationaddress0");
		session.save(organization);
		
		Team team = new Team();
		team.setName("teamname00");
		team.setRating(50);
		team.setOrganization(organization);
		
		Set<Team> teams = new HashSet<>();
		teams.add(team);
		organization.setTeams(teams);
		
		assertNull(team.getId());
		
		session.save(team);
		
		assertNotNull(team.getId());
		assertTrue(team.getId() > 0);
		
		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void testOrganizationTeam1() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Organization organization = new Organization();
		organization.setName("organizationname1");
		organization.setAddress("organizationaddress1");

		Team team1 = new Team();
		team1.setName("teamname11");
		team1.setRating(10);
		team1.setOrganization(organization);
		
		Team team2 = new Team();
		team2.setName("teamname12");
		team2.setRating(20);
		team2.setOrganization(organization);

		Set<Team> teams = new HashSet<>();
		teams.add(team1);
		teams.add(team2);
		
		organization.setTeams(teams);
		
		session.save(organization);
		
		assertNotNull(organization.getId());
		Assert.assertEquals(2, organization.getTeams().size());
		
		for (Team team : organization.getTeams()) {
			assertNotNull(team.getId());
			assertNotNull(team.getName());
		}
		
		session.getTransaction().commit();
		session.close();
	}
	
}
