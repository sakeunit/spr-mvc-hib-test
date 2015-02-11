package com.sprhib.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sprhib.init.OrganizationBuilder;
import com.sprhib.model.Organization;
import com.sprhib.service.impl.OrganizationServiceImpl;

public class OrganizationDAOTest {

	@Mock
	OrganizationDAO mockDAO;
	@InjectMocks
	OrganizationServiceImpl organizationServiceImpl;

	private Organization organization;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		organization = new OrganizationBuilder().withName("test organization")
				.withAddress("test address").build();
	}

	@Test
	public void testAddOrganization() {
		when(mockDAO.getOrganization(1)).thenReturn(organization);

		Organization org = organizationServiceImpl.getOrganization(1);
		verify(mockDAO).getOrganization(1);
		assertEquals("test organization", org.getName());
	}

	@Test
	public void testGetOrganizationByName() {
		String name = "test organization";
		when(mockDAO.getOrganization(name)).thenReturn(organization);

		Organization org = organizationServiceImpl.getOrganization(name);
		verify(mockDAO).getOrganization(name);
		assertEquals(name, org.getName());
	}

}
