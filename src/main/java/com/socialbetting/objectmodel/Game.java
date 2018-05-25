package com.socialbetting.objectmodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "game")
public class Game {

	public static final String STATE_NOT_STARTED = "Not Started";

	public static final String STATE_10_MINUTES_BEFORE_MATCH = "10 minutes before match";

	public static final String STATE_STARTED = "Started";

	public static final String STATE_FINISHED = "Finished";

	private @Id @GeneratedValue Long id;

	@ManyToOne
	@JoinColumn(name = "competition")
	@JsonIgnore
	private Competition competition;

	@ManyToOne
	@JoinColumn(name = "team_id1")
	@JsonIgnore
	private Team team1;

	@ManyToOne
	@JoinColumn(name = "team_id2")
	@JsonIgnore
	private Team team2;

	private long team1_id;

	private long team2_id;

	@Column(name = "goals_team1")
	private String goalsTeam1;

	@Column(name = "goals_team2")
	private String goalsTeam2;

	private String level;

	private String state;

	private String date;

	private Game() {
	}

	public Game(Competition competition, Team team1, Team team2, String level, String state, String date) {
		super();
		this.competition = competition;
		this.team1 = team1;
		this.team1_id = team1.getId();
		this.team2 = team2;
		this.team2_id = team2.getId();
		this.level = level;
		this.state = state;
		this.date = date;
	}

	public Date getLocalDate() {
		SimpleDateFormat formatterUTC = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date localDate = null;
		try {
			localDate = formatterUTC.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return localDate;
	}

}
