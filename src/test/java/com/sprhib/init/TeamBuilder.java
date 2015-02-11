package com.sprhib.init;

import java.util.Set;

import com.sprhib.model.Member;
import com.sprhib.model.Organization;
import com.sprhib.model.Team;

public class TeamBuilder {

	private String name;
	private Organization organization;
	private Set<Member> teamMembers;
	private int rating = 10;

	public TeamBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public TeamBuilder withRating(int rating) {
		this.rating = rating;
		return this;
	}
	
	public TeamBuilder withOrganization(Organization org) {
		this.organization = org;
		return this;
	}

	public Team build() {
		Team team = new Team();
		team.setName(name);
		team.setOrganization(organization);
		team.setMembers(teamMembers);
		team.setRating(rating);
		return team;
	}
}
