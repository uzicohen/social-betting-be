package com.socialbetting.objectmodel;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tournament")
public class Tournament {

	private @Id @GeneratedValue Long id;
	private String name;
	private String photoUrl;
	private String description;

	private Tournament() {
	}

	public Tournament(String name, String photoUrl, String description) {
		this.name = name;
		this.photoUrl = photoUrl;
		this.description = description;
	}
}
