package com.socialbetting.objectmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "tournament")
public class Tournament {

	private @Id @GeneratedValue Long id;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	private String name;

	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "competition")
	private Competition competition;

	@Transient
	private List<Bet> bets;

	public Tournament() {
		this.bets = new ArrayList<>();
	}

	public Tournament(User createdBy, String name, Date dateCreated, Competition competition) {
		super();
		this.createdBy = createdBy;
		this.name = name;
		this.dateCreated = dateCreated;
		this.competition = competition;
		this.bets = new ArrayList<>();
	}

}
