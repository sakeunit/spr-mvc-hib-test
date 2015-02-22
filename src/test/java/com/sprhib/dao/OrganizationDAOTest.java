package com.sprhib.dao;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.impl.OrganizationDAOImpl;
import com.sprhib.init.BaseTestConfig;
import com.sprhib.init.OrganizationBuilder;
import com.sprhib.init.TeamBuilder;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BaseTestConfig.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrganizationDAOTest {

	@Autowired
	private SessionFactory sessionFactory;
	private OrganizationDAOImpl nodeDAO;

	@Before
	public void setup() {
		nodeDAO = new OrganizationDAOImpl(sessionFactory);
	}

	@Test
	public void testOrganizationMultipleTeams() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Organization organization = new OrganizationBuilder()
				.withName("organizationname1")
				.withAddress("organizationaddress1").build();

		Team team1 = new TeamBuilder().withName("teamname11").withRating(10)
				.withOrganization(organization).build();

		Team team2 = new TeamBuilder().withName("teamname12").withRating(9)
				.withOrganization(organization).build();

		Set<Team> teams = new HashSet<>();
		teams.add(team1);
		teams.add(team2);

		organization.setTeams(teams);

		nodeDAO.addOrganization(organization);

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
