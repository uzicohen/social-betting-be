package com.socialbetting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.socialbetting.objectmodel.Bet;
import com.socialbetting.objectmodel.Game;
import com.socialbetting.objectmodel.PersonalPage;
import com.socialbetting.objectmodel.Team;
import com.socialbetting.objectmodel.Tournament;
import com.socialbetting.objectmodel.TournamentUser;
import com.socialbetting.objectmodel.User;
import com.socialbetting.repository.BetRepository;
import com.socialbetting.repository.GameRepository;
import com.socialbetting.repository.TeamRepository;
import com.socialbetting.repository.TournamentRepository;
import com.socialbetting.repository.TournamentUserRepository;
import com.socialbetting.repository.UserRepository;
import com.socialbetting.security.service.SocialBettingUserDetailsService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiControllers {

	@Autowired
	private SocialBettingUserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private TournamentUserRepository tournamentUserRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private BetRepository betRepository;

	@Autowired
	private TeamRepository teamRepository;

	public ApiControllers(SocialBettingUserDetailsService userDetailsService, TournamentRepository tournamentRepository,
			TournamentUserRepository tournamentUserRepository, GameRepository gameRepository,
			TeamRepository teamRepository) {
		super();
		this.userDetailsService = userDetailsService;
		this.tournamentRepository = tournamentRepository;
		this.tournamentUserRepository = tournamentUserRepository;
		this.gameRepository = gameRepository;
		this.teamRepository = teamRepository;
	}

	@CrossOrigin
	@RequestMapping(value = "/users/me", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> currentUserName(Principal principal) {
		String currentPrincipalName = principal.getName();
		User user = (User) userDetailsService.loadUserByUsername(currentPrincipalName);
		return ResponseEntity.ok(user);
	}

	@CrossOrigin
	@RequestMapping(value = "/personalPage", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PersonalPage> getAllTournaments(Principal principal) {
		String username = principal.getName();

		List<Team> teams = (List<Team>) this.teamRepository.findAll();

		List<Tournament> tournaments = this.tournamentRepository.getAllTournaments(username);
		for (Tournament tour : tournaments) {
			tour.setBets(this.betRepository.getAllBets(username, tour));
		}

		PersonalPage presonalPage = new PersonalPage(teams, tournaments);

		return ResponseEntity.ok(presonalPage);
	}

	private static class StringParam {
		private String tournamentId;

		public String getTournamentId() {
			return tournamentId;
		}

		public void setTournamentId(String tournamentId) {
			this.tournamentId = tournamentId;
		}

	}

	@CrossOrigin
	@RequestMapping(value = "/joinTournament", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Boolean> joinTournament(@RequestBody StringParam stringParam, Principal principal) {
		String username = principal.getName();
		User user = (User) this.userDetailsService.loadUserByUsername(username);
		Tournament tournament = this.tournamentRepository.findById(Long.parseLong(stringParam.getTournamentId())).get();
		this.tournamentUserRepository
				.save(new TournamentUser(tournament, user, TournamentUser.TOURNAMENT_ROLE_PARTICIPANT, new Date()));

		// Build all bets for this user in this tournament
		List<Game> games = this.gameRepository.getAllGamesByCompetition(tournament.getCompetition().getId());
		List<Bet> bets = new ArrayList<>();
		for (Game game : games) {
			bets.add(new Bet(user, game, tournament, 0, 0));
		}
		this.betRepository.saveAll(bets);

		return ResponseEntity.ok(true);
	}

}