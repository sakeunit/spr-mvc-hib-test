package com.sprhib.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.impl.TeamDAOImpl;
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
public class TeamDAOTest {

	@Autowired
	private SessionFactory sessionFactory;
	private TeamDAOImpl nodeDAO;

	@Before
	public void setup() {
		nodeDAO = new TeamDAOImpl(sessionFactory);
	}

	@Test
	public void testAddTeamWithOrganization() {
		Organization organization = new OrganizationBuilder()
				.withName("organizationname0")
				.withAddress("organizationaddress0").build();

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(organization);
		session.getTransaction().commit();
		session.close();

		Team team = new TeamBuilder().withName("teamname00").withRating(9)
				.withOrganization(organization).build();

		assertNull(team.getId());

		session = sessionFactory.openSession();
		session.beginTransaction();
		nodeDAO.addTeam(team);
		session.getTransaction().commit();
		session.close();

		assertTrue(team.getId() > 0);
	}

}
