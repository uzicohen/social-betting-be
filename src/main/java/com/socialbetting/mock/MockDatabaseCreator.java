package com.socialbetting.mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.socialbetting.objectmodel.Game;
import com.socialbetting.objectmodel.Team;
import com.socialbetting.objectmodel.Tournament;
import com.socialbetting.objectmodel.TournamentInstance;
import com.socialbetting.objectmodel.TournamentSubscription;
import com.socialbetting.objectmodel.User;
import com.socialbetting.repository.GameRepository;
import com.socialbetting.repository.TeamRepository;
import com.socialbetting.repository.TournamentInstanceRepository;
import com.socialbetting.repository.TournamentRepository;
import com.socialbetting.repository.TournamentSubscriptionRepository;
import com.socialbetting.repository.UserRepository;

@Component
public class MockDatabaseCreator {

	@Autowired
	private UserRepository UserRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private TournamentInstanceRepository tournamentInstanceRepository;

	@Autowired
	private TournamentSubscriptionRepository tournamentSubscriptionRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private TeamRepository teamRepository;

	public MockDatabaseCreator(com.socialbetting.repository.UserRepository userRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder, TournamentRepository tournamentRepository,
			TournamentInstanceRepository tournamentInstanceRepository,
			TournamentSubscriptionRepository tournamentSubscriptionRepository, GameRepository gameRepository,
			TeamRepository teamRepository) {
		super();
		UserRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.tournamentRepository = tournamentRepository;
		this.tournamentInstanceRepository = tournamentInstanceRepository;
		this.tournamentSubscriptionRepository = tournamentSubscriptionRepository;
		this.gameRepository = gameRepository;
		this.teamRepository = teamRepository;
	}

	public void load() throws ParseException {
		User user1 = this.UserRepository.save(
				new User("uzicohen9@gmail.com", this.bCryptPasswordEncoder.encode("uzi636"), true, User.ROLE_ADMIN));

		User user2 = this.UserRepository.save(new User("almog.bregman@gmail.com",
				this.bCryptPasswordEncoder.encode("abcd2106"), true, User.ROLE_USER));

		Tournament tournament = this.tournamentRepository
				.save(new Tournament("World cup 2018", "someurl", "Soccer world cup in Russia"));

		TournamentInstance tournamentInstance = this.tournamentInstanceRepository
				.save(new TournamentInstance(user1, new Date(), tournament));

		this.tournamentSubscriptionRepository.save(new TournamentSubscription(tournamentInstance, user1,
				TournamentSubscription.TOURNAMENT_ROLE_ADMIN, new Date()));

		this.tournamentSubscriptionRepository.save(new TournamentSubscription(tournamentInstance, user2,
				TournamentSubscription.TOURNAMENT_ROLE_PARTICIPANT, new Date()));

		Team italy = this.teamRepository.save(new Team("Italy", "http://someurl", "Italy's national team"));
		Team france = this.teamRepository.save(new Team("France", "http://someurl", "France's national team"));
		Team germany = this.teamRepository.save(new Team("Germany", "http://someurl", "Germany's national team"));
		Team england = this.teamRepository.save(new Team("England", "http://someurl", "England's national team"));

		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

		String dateInString1 = "21-06-2018 10:00:00";
		Date date1 = sdf.parse(dateInString1);
		Game game1 = this.gameRepository.save(new Game(italy, france, date1));

		String dateInString2 = "22-06-2018 10:00:00";
		Date date2 = sdf.parse(dateInString2);
		Game game2 = this.gameRepository.save(new Game(germany, england, date2));

		String dateInString3 = "23-06-2018 12:00:00";
		Date date3 = sdf.parse(dateInString3);
		Game game3 = this.gameRepository.save(new Game(italy, germany, date3));

		String dateInString4 = "23-06-2018 12:00:00";
		Date date4 = sdf.parse(dateInString4);
		Game game4 = this.gameRepository.save(new Game(france, england, date4));

	}

}
