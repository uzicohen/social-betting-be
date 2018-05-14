package com.socialbetting.objectmodel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tournament_instance")
public class TournamentInstance {

	private @Id @GeneratedValue Long id;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "tournament")
	private Tournament tournament;

	private TournamentInstance() {
	}

	public TournamentInstance(User createdBy, Date dateCreated, Tournament tournament) {
		super();
		this.createdBy = createdBy;
		this.dateCreated = dateCreated;
		this.tournament = tournament;
	}

}
