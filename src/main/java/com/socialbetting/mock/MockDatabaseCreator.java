package com.socialbetting.mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.socialbetting.objectmodel.Game;
import com.socialbetting.objectmodel.Team;
import com.socialbetting.objectmodel.Bet;
import com.socialbetting.objectmodel.Competition;
import com.socialbetting.objectmodel.Tournament;
import com.socialbetting.objectmodel.TournamentUser;
import com.socialbetting.objectmodel.User;
import com.socialbetting.repository.GameRepository;
import com.socialbetting.repository.TeamRepository;
import com.socialbetting.repository.TournamentRepository;
import com.socialbetting.repository.BetRepository;
import com.socialbetting.repository.CompetitionRepository;
import com.socialbetting.repository.TournamentUserRepository;
import com.socialbetting.repository.UserRepository;

@Component
public class MockDatabaseCreator {

	@Autowired
	private UserRepository UserRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CompetitionRepository competitionRepository;

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private TournamentUserRepository tournamentUserRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private BetRepository betRepository;

	public MockDatabaseCreator(com.socialbetting.repository.UserRepository userRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder, CompetitionRepository competitionRepository,
			TournamentRepository tournamentRepository, TournamentUserRepository tournamentUserRepository,
			GameRepository gameRepository, TeamRepository teamRepository, BetRepository betRepository) {
		super();
		UserRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.competitionRepository = competitionRepository;
		this.tournamentRepository = tournamentRepository;
		this.tournamentUserRepository = tournamentUserRepository;
		this.gameRepository = gameRepository;
		this.teamRepository = teamRepository;
		this.betRepository = betRepository;
	}

	public void load() throws ParseException {
		User user1 = this.UserRepository.save(new User("uzicohen9@gmail.com", "uzinio",
				this.bCryptPasswordEncoder.encode("uzi636"), true, User.ROLE_ADMIN));

		User user2 = this.UserRepository.save(new User("almog.bregman@gmail.com", "almogi",
				this.bCryptPasswordEncoder.encode("abcd2106"), true, User.ROLE_USER));

		Competition competition1 = this.competitionRepository
				.save(new Competition("World cup 2018", "someurl", "Soccer world cup in Russia"));

		Competition competition2 = this.competitionRepository.save(
				new Competition("NBA playoffs 2018", "someurl", "National Basketball association playoffs of 2018"));

		Tournament tournament1 = this.tournamentRepository
				.save(new Tournament(user1, "Belgrad 2020 - World cup", new Date(), competition1));

		Tournament tournament2 = this.tournamentRepository
				.save(new Tournament(user1, "Belgrad 2020 - NBA", new Date(), competition2));

		Tournament tournament3 = this.tournamentRepository
				.save(new Tournament(user1, "My friends from the army", new Date(), competition1));

		/*
		 * 
		 * Tournament-1 (World-Cup): User-1 User-2
		 * 
		 * Tournament-2 (NBA): User-1
		 * 
		 * Tournament-3 (World-Cup): User-2
		 * 
		 */

		TournamentUser tournamentUser1 = new TournamentUser(tournament1, user1, TournamentUser.TOURNAMENT_ROLE_ADMIN,
				new Date());
		tournamentUser1.setScore(15);
		this.tournamentUserRepository.save(tournamentUser1);

		TournamentUser tournamentUser2 = new TournamentUser(tournament2, user1, TournamentUser.TOURNAMENT_ROLE_ADMIN,
				new Date());
		tournamentUser2.setScore(12);
		this.tournamentUserRepository.save(tournamentUser2);

//		TournamentUser tournamentUser3 = new TournamentUser(tournament3, user1, TournamentUser.TOURNAMENT_ROLE_ADMIN,
//				new Date());
//		tournamentUser3.setScore(12);
//		this.tournamentUserRepository.save(tournamentUser3);

		TournamentUser tournamentUser4 = new TournamentUser(tournament3, user2,
				TournamentUser.TOURNAMENT_ROLE_PARTICIPANT, new Date());
		tournamentUser4.setScore(22);
		this.tournamentUserRepository.save(tournamentUser4);

		TournamentUser tournamentUser5 = new TournamentUser(tournament1, user2,
				TournamentUser.TOURNAMENT_ROLE_PARTICIPANT, new Date());
		tournamentUser5.setScore(22);
		this.tournamentUserRepository.save(tournamentUser5);

		Team italy = this.teamRepository.save(new Team("Italy", "http://someurl", "Italy's national team"));
		Team france = this.teamRepository.save(new Team("France", "http://someurl", "France's national team"));
		Team germany = this.teamRepository.save(new Team("Germany", "http://someurl", "Germany's national team"));
		Team england = this.teamRepository.save(new Team("England", "http://someurl", "England's national team"));

		Team chicago = this.teamRepository.save(new Team("Chicago", "http://someurl", "Chicago basketball team"));
		Team goldenState = this.teamRepository
				.save(new Team("Golden State", "http://someurl", "GOlden State basketball team"));
		Team cleveland = this.teamRepository.save(new Team("Cleveland", "http://someurl", "Cleveland basketball team"));
		Team dalls = this.teamRepository.save(new Team("Dallas", "http://someurl", "Dallas basketball team"));

		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

		String dateInString1 = "21-06-2018 10:00:00";
		Date date1 = sdf.parse(dateInString1);
		Game game1 = this.gameRepository.save(new Game(competition1, italy, france, date1));

		String dateInString2 = "22-06-2018 10:00:00";
		Date date2 = sdf.parse(dateInString2);
		Game game2 = this.gameRepository.save(new Game(competition1, germany, england, date2));

		String dateInString3 = "23-06-2018 12:00:00";
		Date date3 = sdf.parse(dateInString3);
		Game game3 = this.gameRepository.save(new Game(competition1, italy, germany, date3));

		String dateInString4 = "23-06-2018 12:00:00";
		Date date4 = sdf.parse(dateInString4);
		Game game4 = this.gameRepository.save(new Game(competition1, france, england, date4));

		String dateInString5 = "25-06-2018 12:00:00";
		Date date5 = sdf.parse(dateInString5);
		Game game5 = this.gameRepository.save(new Game(competition2, chicago, goldenState, date5));

		String dateInString6 = "28-06-2018 10:00:00";
		Date date6 = sdf.parse(dateInString6);
		Game game6 = this.gameRepository.save(new Game(competition2, chicago, cleveland, date6));

		String dateInString7 = "01-07-2018 12:00:00";
		Date date7 = sdf.parse(dateInString7);
		Game game7 = this.gameRepository.save(new Game(competition2, goldenState, dalls, date7));

		String dateInString8 = "01-07-2018 15:00:00";
		Date date8 = sdf.parse(dateInString8);
		Game game8 = this.gameRepository.save(new Game(competition2, dalls, cleveland, date8));

		// Tournament-1
		Bet bet1 = this.betRepository.save(new Bet(user1, game1, tournament1, 1, 0, new Date()));
		Bet bet2 = this.betRepository.save(new Bet(user1, game2, tournament1, 1, 2, new Date()));
		Bet bet3 = this.betRepository.save(new Bet(user1, game3, tournament1, 1, 4, new Date()));
		Bet bet4 = this.betRepository.save(new Bet(user1, game4, tournament1, 3, 3, new Date()));
		
		Bet bet5 = this.betRepository.save(new Bet(user2, game1, tournament1, 2, 2, new Date()));
		Bet bet6 = this.betRepository.save(new Bet(user2, game2, tournament1, 3, 2, new Date()));
		Bet bet7 = this.betRepository.save(new Bet(user2, game3, tournament1, 5, 4, new Date()));
		Bet bet8 = this.betRepository.save(new Bet(user2, game4, tournament1, 3, 1, new Date()));

	}

}
