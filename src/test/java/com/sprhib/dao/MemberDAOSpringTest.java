package com.sprhib.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sprhib.init.BaseTestConfig;
import com.sprhib.model.Member;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseTestConfig.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MemberDAOSpringTest {

	@Autowired
	private MemberDAO teamMemberDAO;
	@Autowired
	private SessionFactory sessionFactory;

	@Test
	public void testAddMember() {
		Member teamMember = createMember("first-testAddMember",
				"last-testAddMember");
		assertNull(teamMember.getId());
		teamMemberDAO.addMember(teamMember);
		assertNotNull(teamMember.getId());
	}

	@Test
	public void testAddTeamToMember() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// org and team
		Organization org1 = new Organization();
		org1.setName("organization-testAddTeamToMember-1");
		org1.setAddress("address-test-1");
		session.save(org1);

		Team team1 = new Team();
		team1.setName("team-testAddTeamToMember-1");
		team1.setRating(5);
		team1.setOrganization(org1);
		assertNull(team1.getId());
		session.save(team1);

		// team members
		Set<Team> teams = new HashSet<>();
		teams.add(team1);

		Member teamMember = createMember("first-testAddTeamToMember",
				"last-testAddTeamToMember");
		teamMember.setTeams(teams);

		teamMemberDAO.addMember(teamMember);

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
	@Test
	public void testAddMemberToMultipleTeams() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Organization org1 = new Organization();
		org1.setName("organization-testAddTeamMemberMultiple-1");
		org1.setAddress("address-test-1");
		session.save(org1);

		Organization org2 = new Organization();
		org2.setName("organization-testAddTeamMemberMultiple-2");
		org2.setAddress("address-test-2");
		session.save(org2);

		Team team1 = new Team();
		team1.setName("team-testAddTeamMemberMultiple-1");
		team1.setRating(5);
		team1.setOrganization(org1);
		assertNull(team1.getId());
		session.save(team1);

		Team team2 = new Team();
		team2.setName("team-testAddTeamMemberMultiple-2");
		team2.setRating(50);
		team2.setOrganization(org2);
		assertNull(team2.getId());
		session.save(team2);

		Set<Team> teams = new HashSet<>();
		teams.add(team1);
		teams.add(team2);

		Member teamMember = createMember("first-testAddTeamMemberMultiple",
				"last-testAddTeamMemberMultiple");
		teamMember.setTeams(teams);

		teamMemberDAO.addMember(teamMember);

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

	private Member createMember(String firstName, String lastName) {
		Member teamMember = new Member();
		teamMember.setFirstName(firstName);
		teamMember.setLastName(lastName);
		return teamMember;
	}
}
