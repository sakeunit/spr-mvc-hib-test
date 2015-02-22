package com.sprhib.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sprhib.dao.TeamDAO;
import com.sprhib.init.OrganizationBuilder;
import com.sprhib.init.TeamBuilder;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;
import com.sprhib.service.impl.TeamServiceImpl;

public class TeamServiceTest {

	@Mock
	private TeamDAO mockDAO;
	@InjectMocks
	private TeamService teamService = new TeamServiceImpl();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void testGetTeamById() {
		Organization organization = new OrganizationBuilder().withName(
				"organization name").build();
		
		Team team = new TeamBuilder().withName("team name").withRating(9)
				.withOrganization(organization).build();
		
		when(mockDAO.getTeam(1)).thenReturn(team);
		
		Team t = teamService.getTeam(1);
		verify(mockDAO).getTeam(1);
		assertEquals("team name", t.getName());
	}
	
	@Test
	public void testGetTeamByName() {
		Organization organization = new OrganizationBuilder().withName(
				"organization name").build();
		
		Team team = new TeamBuilder().withName("team name").withRating(9)
				.withOrganization(organization).build();
		
		when(mockDAO.getTeamByName("team name")).thenReturn(team);
		
		Team t = teamService.getTeam("team name");
		verify(mockDAO).getTeamByName("team name");
		assertEquals("team name", t.getName());
	}

	@Test
	public void testListTeams() {
		//TODO
	}
}
