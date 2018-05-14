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
@Table(name = "game")
public class Game {

	private @Id @GeneratedValue Long id;

	@ManyToOne
	@JoinColumn(name = "team_id1")
	private Team team1;

	@ManyToOne
	@JoinColumn(name = "team_id2")
	private Team team2;

	private Date date;

	private Game() {
	}

	public Game(Team team1, Team team2, Date date) {
		super();
		this.team1 = team1;
		this.team2 = team2;
		this.date = date;
	}

}
