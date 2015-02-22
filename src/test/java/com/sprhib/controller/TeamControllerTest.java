package com.sprhib.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import com.sprhib.init.OrganizationBuilder;
import com.sprhib.init.TeamBuilder;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;
import com.sprhib.service.OrganizationService;
import com.sprhib.service.TeamService;

public class TeamControllerTest {

	MockMvc mockMvc;

	@Mock
	TeamService teamService;

	@Mock
	OrganizationService organizationService;

	@InjectMocks
	TeamController controller;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testAddTeam() throws Exception {
		Organization org = new OrganizationBuilder().withName(
				"Organization name").build();
		when(organizationService.getOrganizations()).thenReturn(
				Arrays.asList(org));

		mockMvc.perform(get("/team/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("add-team-form"))
				.andExpect(model().attributeExists("team"))
				.andExpect(
						model().attribute("team",
								hasProperty("name", nullValue())))
				.andExpect(
						model().attribute("team",
								hasProperty("rating", nullValue())))
				.andExpect(
						model().attribute("organizations", Arrays.asList(org)));

		verifyZeroInteractions(teamService);
	}

	@Test
	public void testAddTeamEmptyDoesNotProceed() throws Exception {
		Team team = new TeamBuilder().withName("Team name").build();

		mockMvc.perform(post("/team/add").sessionAttr("team", team))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("home"))
				.andExpect(
						model().attribute("team",
								hasProperty("name", nullValue())))
				.andExpect(
						model().attribute("team",
								hasProperty("organization", nullValue())));
		verifyZeroInteractions(teamService);
	}

	@Test
	public void testAddTeamWithValidParameters() throws Exception {
		Organization org = new OrganizationBuilder()
				.withName("Organization name").withId(1).build();
		Team team = new TeamBuilder().withName("Team name")
				.withOrganization(org).build();

		mockMvc.perform(
				post("/team/add").param("name", "Team name")
						.param("rating", "9")
						.param("organization.name", "Organization name")
						.param("organization.id", "1")
						.sessionAttr("team", new Team())
						.sessionAttr("organizations", Arrays.asList(org)))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(forwardedUrl("home"))
				.andExpect(
						model().attribute("team",
								hasProperty("name", is("Team name"))))
				.andExpect(
						model().attribute("team",
								hasProperty("organization", equalTo(org))));

		verify(teamService, times(1)).addTeam(team);
	}
}
