package com.socialbetting.objectmodel;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "bet")
public class Bet {

	private @Id @GeneratedValue Long id;

	@OneToOne
	@JoinColumn(name = "_user")
	@JsonIgnore
	private User user;

	@ManyToOne
	@JoinColumn(name = "game")
	private Game game;

	@ManyToOne
	@JoinColumn(name = "tournament")
	@JsonIgnore
	private Tournament tournament;

	private int team1;

	private int team2;

	private String date;

	private Bet() {
	}

	public Bet(User user, Game game, Tournament tournament, int team1, int team2) {
		super();
		this.user = user;
		this.game = game;
		this.tournament = tournament;
		this.team1 = team1;
		this.team2 = team2;
		this.date = game.getDate();
	}

}
