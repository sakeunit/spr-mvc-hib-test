package com.sprhib.init;

import java.util.Set;

import com.sprhib.model.Member;
import com.sprhib.model.Team;

public class MemberBuilder {

	private String firstName;
	private String lastName;
	private Set<Team> teams;

	public Member build() {
		Member member = new Member();
		member.setFirstName(firstName);
		member.setLastName(lastName);
		member.setTeams(teams);
		return member;
	}

	public MemberBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public MemberBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

}
