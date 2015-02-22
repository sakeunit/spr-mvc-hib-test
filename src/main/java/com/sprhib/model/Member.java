package com.sprhib.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue
	private Integer id;

	private String firstName;

	private String lastName;

	@OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name="team_member_link",
    joinColumns={@JoinColumn(name="member_id", referencedColumnName="id")},  
    inverseJoinColumns={@JoinColumn(name="team_id", referencedColumnName="id")})
	private Set<Team> teams;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Member) {
			Member other = (Member) obj;
			if (this.id != null && other.getId() != null) {
				return this.id.equals(other.getId());
			}
			String nameLabel = lastName + "-" + firstName;
			String otherNameLabel = other.lastName + "-" + other.firstName;
			return nameLabel.equals(otherNameLabel);
		}
		return super.equals(obj);
	}
}
