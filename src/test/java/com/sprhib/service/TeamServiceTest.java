package com.sprhib.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.TeamDAO;
import com.sprhib.init.BaseTestConfig;
import com.sprhib.init.OrganizationBuilder;
import com.sprhib.init.TeamBuilder;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;
import com.sprhib.service.impl.TeamServiceImpl;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseTestConfig.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TeamServiceTest {

	@Autowired
	private TeamDAO teamDAO;

	@Autowired
	private TeamService teamService = new TeamServiceImpl();

	@Test
	public void testAddTeam() {
		Organization organization = new OrganizationBuilder().withName(
				"organization name").build();

		Team team = new TeamBuilder().withName("teamname").withRating(50)
				.withOrganization(organization).build();
		assertNull(team.getId());

		teamService.addTeam(team);

		assertNotNull(team.getId());
		assertTrue(team.getId() > 0);
	}

}
