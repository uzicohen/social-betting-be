package com.socialbetting.objectmodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tournament_user")
public class TournamentUser {

	public static final String TOURNAMENT_ROLE_ADMIN = "tournament_role_admin";

	public static final String TOURNAMENT_ROLE_PARTICIPANT = "tournament_role_participant";

	private @Id @GeneratedValue Long id;

	@ManyToOne
	@JoinColumn(name = "tournament")
	private Tournament tournament;

	@ManyToOne
	@JoinColumn(name = "_user")
	private User user;

	private String role;

	@Column(name = "date_joined")
	private Date dateJoined;

	private int score;

	public TournamentUser() {
	}

	public TournamentUser(Tournament tournament, User user, String role, Date dateJoined) {
		super();
		this.tournament = tournament;
		this.user = user;
		this.role = role;
		this.dateJoined = dateJoined;
		this.score = 0;
	}

}
