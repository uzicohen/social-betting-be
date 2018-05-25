package com.socialbetting.objectmodel;

import java.util.HashMap;
import java.util.List;

public class PersonalPage {

	private HashMap<Long, Team> teams;

	private List<Tournament> tournaments;

	public PersonalPage(List<Team> teams, List<Tournament> tournaments) {
		super();
		this.teams = new HashMap<>();
		for (Team team : teams) {
			this.teams.put(team.getId(), team);
		}
		this.tournaments = tournaments;
	}

	public HashMap<Long, Team> getTeams() {
		return teams;
	}

	public void setTeams(HashMap<Long, Team> teams) {
		this.teams = teams;
	}

	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

}
