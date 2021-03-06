package com.sprhib.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "organization")
public class Organization {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private String telephone;

	private String address;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
	private Set<Team> teams;

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Organization) {
			Organization other = (Organization) obj;
			if (this.id != null && other.getId() != null) {
				return this.id.equals(other.getId());
			}
			if (this.name != null && other.getName() != null) {
				return this.name.equals(other.getName());
			}
		}
		return super.equals(obj);
	}
}
