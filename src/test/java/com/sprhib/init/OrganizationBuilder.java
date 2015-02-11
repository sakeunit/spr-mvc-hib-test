package com.sprhib.init;

import java.util.Set;

import com.sprhib.model.Organization;
import com.sprhib.model.Team;

public class OrganizationBuilder {

	private String name = "Default Inc";
	private String address = "Island, Sea";
	private String telephone = "+40403030-04243629383";
	private Set<Team> teams;
	private int id;

	public OrganizationBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public OrganizationBuilder withAddress(String address) {
		this.address = address;
		return this;
	}

	public OrganizationBuilder withTelephone(String telephone) {
		this.telephone = telephone;
		return this;
	}

	public OrganizationBuilder withTeams(Set<Team> teams) {
		this.teams = teams;
		return this;
	}

	public Organization build() {
		Organization o = new Organization();
		o.setId(id);
		o.setName(name);
		o.setAddress(address);
		o.setTeams(teams);
		o.setTelephone(telephone);
		return o;
	}

	public OrganizationBuilder withId(int id) {
		this.id = id;
		return this;
	}
}
