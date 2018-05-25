package com.socialbetting.objectmodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "competition")
public class Competition {

	private @Id @GeneratedValue Long id;

	private String name;

	@Column(name = "photo_url")
	private String photoUrl;

	private String description;

	private Competition() {
	}

	public Competition(String name, String photoUrl, String description) {
		this.name = name;
		this.photoUrl = photoUrl;
		this.description = description;
	}
}
