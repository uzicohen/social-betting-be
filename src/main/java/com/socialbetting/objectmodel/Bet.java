package com.socialbetting.objectmodel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bet")
public class Bet {

	private @Id @GeneratedValue Long id;

	@OneToOne
	@JoinColumn(name = "_user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "game")
	private Game game;

	@ManyToOne
	@JoinColumn(name = "tournament_instance")
	private TournamentInstance tournamentInstance;

	private int team1;

	private int team2;

	private Date date;

	private Bet() {
	}

	public Bet(User user, Game game, TournamentInstance tournamentInstance, int team1, int team2, Date date) {
		super();
		this.user = user;
		this.game = game;
		this.tournamentInstance = tournamentInstance;
		this.team1 = team1;
		this.team2 = team2;
		this.date = date;
	}

}
