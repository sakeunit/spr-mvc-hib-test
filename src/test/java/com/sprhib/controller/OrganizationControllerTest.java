package com.sprhib.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sprhib.model.Organization;
import com.sprhib.service.OrganizationService;

public class OrganizationControllerTest {

	MockMvc mockMvc;

	@Mock
	OrganizationService organizationService;

	@InjectMocks
	OrganizationController controller;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testAddOrganization() throws Exception {
		mockMvc.perform(get("/organization/add"))
				.andExpect(status().isOk())
				.andExpect(view().name("add-organization-form"))
				.andExpect(model().attributeExists("organization"))
				.andExpect(
						model().attribute("organization",
								hasProperty("name", nullValue())))
				.andExpect(
						model().attribute("organization",
								hasProperty("address", nullValue())));
		verifyZeroInteractions(organizationService);
	}

	@Test
	public void testAddOrganizationEmptyDoesNotProceed() throws Exception {
		Organization organizationToAdd = new Organization();

		mockMvc.perform(
				post("/organization/add").sessionAttr("organization",
						organizationToAdd))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("home"))
				.andExpect(
						model().attribute("organization",
								hasProperty("name", nullValue())))
				.andExpect(
						model().attribute("organization",
								hasProperty("address", nullValue())));
		verifyZeroInteractions(organizationService);
	}

	@Test
	public void testAddOrganizationWithValidParameters() throws Exception {
		Organization organizationToAdd = new Organization();

		mockMvc.perform(
				post("/organization/add").param("name", "organization name")
						.param("address", "organization address")
						.sessionAttr("organization", organizationToAdd))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("home"))
				.andExpect(
						model().attribute("organization",
								hasProperty("name", is("organization name"))))
				.andExpect(
						model().attribute(
								"organization",
								hasProperty("address",
										is("organization address"))));
		verify(organizationService, times(1))
				.addOrganization(organizationToAdd);
	}
}
