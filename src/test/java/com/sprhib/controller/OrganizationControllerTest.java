package com.sprhib.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;

import com.sprhib.init.OrganizationBuilder;
import com.sprhib.model.Organization;
import com.sprhib.service.OrganizationService;
import com.sprhib.validator.OrganizationValidator;

public class OrganizationControllerTest {

	MockMvc mockMvc;

	@Mock
	OrganizationService organizationService;

	@Mock
	OrganizationValidator validator;

	@InjectMocks
	OrganizationController controller;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		when(validator.supports(any(Class.class))).thenReturn(true);
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

		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock)
					throws Throwable {
				Errors errors = (Errors) invocationOnMock.getArguments()[1];
				errors.reject("forced error");
				return null;
			}
		}).when(validator).validate(anyObject(), any(Errors.class));

		mockMvc.perform(
				post("/organization/add").sessionAttr("organization",
						new Organization()))
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
		Organization organizationToAdd = new OrganizationBuilder()
				.withName("organization name")
				.withAddress("organization address").withId(1).build();

		when(organizationService.addOrganization(any(Organization.class)))
				.thenReturn(1);

		mockMvc.perform(
				post("/organization/add").param("name", "organization name")
						.param("address", "organization address")
						.sessionAttr("organization", new Organization()))
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

	@Test
	public void testAddOrganizationWhenItAlreadyExists() throws Exception {
		Organization organizationAdded = new OrganizationBuilder()
				.withName("organization name")
				.withAddress("organization address").withId(1).build();

		when(organizationService.getOrganization("organization name"))
				.thenReturn(organizationAdded);

		mockMvc.perform(
				post("/organization/add").param("name", "organization name")
						.param("address", "organization address")
						.sessionAttr("organization", new Organization()))
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
		verify(organizationService, times(1)).getOrganization(
				"organization name");
		verify(organizationService, times(0))
				.addOrganization(organizationAdded);
	}
}
