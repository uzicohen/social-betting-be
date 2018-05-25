package com.socialbetting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialbetting.objectmodel.Game;

public interface GameRepository extends CrudRepository<Game, Long> {

	@Query("SELECT g FROM Game g INNER JOIN Competition c ON g.competition = c.id WHERE c.id=:competitionId")
	public List<Game> getAllGamesByCompetition(@Param("competitionId") long competitionId);

}
