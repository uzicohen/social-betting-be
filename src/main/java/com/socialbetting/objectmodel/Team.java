package com.socialbetting.objectmodel;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "team")
public class Team {

	private @Id @GeneratedValue Long id;
	private String name;
	private String photoUrl;
	private String description;

	private Team() {
	}

	public Team(String name, String photoUrl, String description) {
		this.name = name;
		this.photoUrl = photoUrl;
		this.description = description;
	}
}
