package com.socialbetting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialbetting.objectmodel.Bet;
import com.socialbetting.objectmodel.Tournament;

public interface BetRepository extends CrudRepository<Bet, Long> {
	@Query("SELECT b FROM Bet b INNER JOIN _user u ON b.user = u.id WHERE u.username=:username AND b.tournament=:tournament")
	List<Bet> getAllBets(@Param("username") String username, @Param("tournament") Tournament tournament);
}
