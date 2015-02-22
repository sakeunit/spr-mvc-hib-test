package com.sprhib.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.dao.impl.MemberDAOImpl;
import com.sprhib.init.BaseTestConfig;
import com.sprhib.init.MemberBuilder;
import com.sprhib.init.OrganizationBuilder;
import com.sprhib.init.TeamBuilder;
import com.sprhib.model.Member;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = BaseTestConfig.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MemberDAOTest {

	@Autowired
	private SessionFactory sessionFactory;
	private MemberDAOImpl nodeDAO;

	@Before
	public void setup() {
		nodeDAO = new MemberDAOImpl(sessionFactory);
	}

	@Rollback(true)
	@Test
	public void testAddMember() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Member teamMember = new MemberBuilder()
				.withFirstName("first-testAddMember")
				.withLastName("last-testAddMember").build();
		assertNull(teamMember.getId());
		nodeDAO.addMember(teamMember);
		assertNotNull(teamMember.getId());

		session.getTransaction().commit();
		session.close();
	}

	@Rollback(true)
	@Test
	public void testAddTeamToMember() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// org and team
		Organization org1 = new OrganizationBuilder()
				.withName("organization-testAddTeamToMember-1")
				.withAddress("address-test-1").build();
		session.save(org1);

		Team team1 = new TeamBuilder().withName("team-testAddTeamToMember-1")
				.withRating(5).withOrganization(org1).build();
		assertNull(team1.getId());
		session.save(team1);

		// team members
		Set<Team> teams = new HashSet<>();
		teams.add(team1);

		Member teamMember = new MemberBuilder()
				.withFirstName("first-testAddTeamToMember")
				.withLastName("last-testAddTeamToMember").build();
		teamMember.setTeams(teams);
		nodeDAO.addMember(teamMember);

		Set<Member> membersTeam1 = new HashSet<>();
		membersTeam1.add(teamMember);
		team1.setMembers(membersTeam1);

		assertNotNull(teamMember.getId());
		assertTrue(teamMember.getId() > 0);
		assertNotNull(teamMember.getTeams());
		Assert.assertEquals(1, teamMember.getTeams().size());

		for (Team team : teamMember.getTeams()) {
			assertNotNull(team.getId());
			assertTrue(team.getId() > 0);
		}

		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Two teams belong to two different organizations One member belongs to two
	 * teams
	 * 
	 */
	@Rollback(true)
	@Test
	public void testAddMemberToMultipleTeams() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Organization org1 = new OrganizationBuilder()
				.withName("organization-testAddTeamMemberMultiple-1")
				.withAddress("address-test-1").build();
		session.save(org1);

		Organization org2 = new OrganizationBuilder()
				.withName("organization-testAddTeamMemberMultiple-2")
				.withAddress("address-test-2").build();
		session.save(org2);

		Team team1 = new TeamBuilder()
				.withName("team-testAddTeamMemberMultiple-1").withRating(5)
				.build();
		team1.setOrganization(org1);
		assertNull(team1.getId());
		session.save(team1);

		Team team2 = new TeamBuilder()
				.withName("team-testAddTeamMemberMultiple-2").withRating(50)
				.build();
		team2.setOrganization(org2);
		assertNull(team2.getId());
		session.save(team2);

		Set<Team> teams = new HashSet<>();
		teams.add(team1);
		teams.add(team2);

		Member teamMember = new MemberBuilder()
				.withFirstName("first-testAddTeamMemberMultiple")
				.withLastName("last-testAddTeamMemberMultiple").build();
		teamMember.setTeams(teams);
		nodeDAO.addMember(teamMember);

		Set<Member> membersTeam1 = new HashSet<>();
		membersTeam1.add(teamMember);
		team1.setMembers(membersTeam1);

		Set<Member> membersTeam2 = new HashSet<>();
		membersTeam2.add(teamMember);
		team2.setMembers(membersTeam2);

		assertNotNull(teamMember.getId());
		assertTrue(teamMember.getId() > 0);
		assertNotNull(teamMember.getTeams());
		Assert.assertEquals(2, teamMember.getTeams().size());

		for (Team team : teamMember.getTeams()) {
			assertNotNull(team.getId());
			assertTrue(team.getId() > 0);
		}

		session.getTransaction().commit();
		session.close();
	}

}
