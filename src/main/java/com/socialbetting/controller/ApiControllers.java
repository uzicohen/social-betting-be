package com.socialbetting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.socialbetting.objectmodel.Bet;
import com.socialbetting.objectmodel.Tournament;
import com.socialbetting.objectmodel.User;
import com.socialbetting.repository.BetRepository;
import com.socialbetting.repository.TournamentRepository;
import com.socialbetting.repository.UserRepository;
import com.socialbetting.security.service.SocialBettingUserDetailsService;
import java.security.Principal;
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
	private BetRepository betRepository;

	public ApiControllers(SocialBettingUserDetailsService userDetailsService,
			TournamentRepository tournamentRepository) {
		super();
		this.userDetailsService = userDetailsService;
		this.tournamentRepository = tournamentRepository;
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
	@RequestMapping(value = "/tournaments", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Tournament>> getAllTournaments(Principal principal) {
		String username = principal.getName();
		List<Tournament> allTours = this.tournamentRepository.getAllTournaments(username);
		return ResponseEntity.ok(allTours);
	}

	@CrossOrigin
	@RequestMapping(value = "/bets", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Bet>> getAllBets(Principal principal, @RequestParam("tournament") long tournament) {
		String username = principal.getName();
		Tournament t = new Tournament();
		t.setId(tournament);
		List<Bet> allBets = this.betRepository.getAllBets(username, t);
		return ResponseEntity.ok(allBets);
	}

}