package com.sprhib.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private Integer rating;

	@ManyToOne
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@OneToMany(mappedBy="id", targetEntity=Team.class)
	private Set<Member> members;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> teamMembers) {
		this.members = teamMembers;
	}

	@Override
	public String toString() {
		String s = name == null ? "NULL" : name;
		s = organization == null ? s + ":NULL" : s + ":" + organization;
		return s;
	}

}