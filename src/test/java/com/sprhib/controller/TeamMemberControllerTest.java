package com.sprhib.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sprhib.init.MemberBuilder;
import com.sprhib.init.TeamBuilder;
import com.sprhib.model.Member;
import com.sprhib.model.Team;
import com.sprhib.service.TeamMemberService;
import com.sprhib.service.TeamService;

public class TeamMemberControllerTest {

	MockMvc mockMvc;

	@Mock
	TeamMemberService teamMemberService;

	@Mock
	TeamService teamService;

	@InjectMocks
	TeamMemberController controller;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testAddTeamMember() throws Exception {
		mockMvc.perform(get("/teammember/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("add-teammember-form"))
				.andExpect(model().attributeExists("teammember"))
				.andExpect(model().attributeExists("teams"))
				.andExpect(
						model().attribute("teammember",
								hasProperty("firstName", nullValue())))
				.andExpect(
						model().attribute("teammember",
								hasProperty("lastName", nullValue())));

		verifyZeroInteractions(teamMemberService);
	}

	@Test
	public void testAddTeamMemberEmptyDoesNotProceed() throws Exception {
		mockMvc.perform(
				post("/teammember/add").sessionAttr("teammember", new Member()))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("home"))
				.andExpect(model().hasErrors())
				.andExpect(
						model().attribute("teammember",
								hasProperty("firstName", nullValue())))
				.andExpect(
						model().attribute("teammember",
								hasProperty("lastName", nullValue())));
		verifyZeroInteractions(teamMemberService);
	}

	@Test
	public void testAddTeamMemberWithValidParameters() throws Exception {
		Team team1 = new TeamBuilder().withName("Team Name Test 1").withId(1)
				.build();
		Team team2 = new TeamBuilder().withName("Team Name Test 2").withId(2)
				.build();

		when(teamService.getTeam(1)).thenReturn(team1);
		when(teamService.getTeam(2)).thenReturn(team2);

		Member memberToAdd = new MemberBuilder()
				.withFirstName("First Name Test")
				.withLastName("Last Name Test").withTeam(team1).build();

		mockMvc.perform(
				post("/teammember/add").param("firstName", "First Name Test")
						.param("lastName", "Last Name Test")
						.param("teams", "1").param("teams", "2")
						.sessionAttr("teammember", new Member())
						.sessionAttr("teams", Arrays.asList(team1, team2)))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("home"))
				.andExpect(model().hasNoErrors())
				.andExpect(model().attribute("teammember", hasProperty("firstName", is("First Name Test"))))
				.andExpect(model().attribute("teammember", hasProperty("lastName", is("Last Name Test"))))
				.andExpect(model().attribute("teammember", hasProperty("teams", hasItem(team1))))
				.andExpect(model().attribute("teammember", hasProperty("teams", hasItem(team2))));

		verify(teamMemberService, times(1)).addMember(memberToAdd);
	}
}
