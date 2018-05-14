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
@Table(name = "tournament_subscription")
public class TournamentSubscription {

	public static final String TOURNAMENT_ROLE_ADMIN = "tournament_role_admin";
	
	public static final String TOURNAMENT_ROLE_PARTICIPANT = "tournament_role_participant";

	private @Id @GeneratedValue Long id;

	@ManyToOne
	@JoinColumn(name = "tournament_instance")
	private TournamentInstance tournamentInstance;

	@ManyToOne
	@JoinColumn(name = "_user")
	private User user;

	private String role;

	private Date dateJoined;

	public TournamentSubscription() {
	}

	public TournamentSubscription(TournamentInstance tournamentIntance, User user, String role, Date dateJoined) {
		super();
		this.tournamentInstance = tournamentIntance;
		this.user = user;
		this.role = role;
		this.dateJoined = dateJoined;
	}

}
