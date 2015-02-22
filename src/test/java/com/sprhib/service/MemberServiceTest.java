package com.sprhib.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sprhib.dao.MemberDAO;
import com.sprhib.init.MemberBuilder;
import com.sprhib.init.OrganizationBuilder;
import com.sprhib.init.TeamBuilder;
import com.sprhib.model.Member;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;
import com.sprhib.service.impl.MemberServiceImpl;

public class MemberServiceTest {

	@Mock
	MemberDAO mockDAO;
	@InjectMocks
	MemberServiceImpl memberServiceImpl;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetMemberById() {
		// TODO
	}

	@Test
	public void testGetMemberByNames() {
		Organization organization = new OrganizationBuilder().withName(
				"organization name").build();

		Team team = new TeamBuilder().withName("team name").withRating(9)
				.withOrganization(organization).build();

		Member member = new MemberBuilder().withFirstName("first name")
				.withLastName("last name").withTeam(team).build();

		when(mockDAO.getMemberByNames("last name", "first name")).thenReturn(
				member);

		Member m = memberServiceImpl.getMember("last name", "first name");
		verify(mockDAO).getMemberByNames("last name", "first name");
		assertEquals("first name", m.getFirstName());
		assertEquals("last name", m.getLastName());
	}

	@Test
	public void testListMembers() {
		// TODO
	}
}
