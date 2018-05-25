package com.socialbetting.objectmodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "team")
public class Team {

	private @Id Long id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "competition")
	@JsonIgnore
	private Competition competition;

	@Column(name = "fifa_code")
	private String fifaCode;

	private String flag;

	private Team() {
	}

	public Team(long id, Competition competition, String name, String fifaCode, String flag) {
		this.id = id;
		this.competition = competition;
		this.name = name;
		this.fifaCode = fifaCode;
		this.flag = flag;
	}

}
