package com.sprhib.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sprhib.dao.OrganizationDAO;
import com.sprhib.model.Organization;

@Repository
public class OrganizationDAOImpl implements OrganizationDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addOrganization(Organization organization) {
		getCurrentSession().save(organization);
	}

	@Override
	public void updateOrganization(Organization organization) {
		Organization organizationToUpdate = getOrganization(organization
				.getId());
		organizationToUpdate.setName(organization.getName());
		organizationToUpdate.setTelephone(organization.getTelephone());
		organizationToUpdate.setAddress(organization.getAddress());
		getCurrentSession().update(organizationToUpdate);
	}

	@Override
	public Organization getOrganization(int id) {
		Organization organization = (Organization) getCurrentSession().get(
				Organization.class, id);
		return organization;
	}

	@Override
	public void deleteOrganization(int id) {
		Organization organization = getOrganization(id);
		if (organization != null) {
			getCurrentSession().delete(organization);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getOrganizations() {
		return getCurrentSession().createQuery("from Organization").list();
	}

	@Override
	public Organization getOrganization(String name) {
		return (Organization) getCurrentSession()
				.createQuery("from Organization where name=:n").setParameter("n", name)
				.uniqueResult();
	}

}
