package com.socialbetting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.socialbetting.objectmodel.Tournament;

public interface TournamentRepository extends CrudRepository<Tournament, Long> {

	@Query("SELECT t FROM Tournament t INNER JOIN TournamentUser tu ON t.id = tu.tournament INNER JOIN _user u ON tu.user = u.id WHERE u.username=:username")
	public List<Tournament> getAllTournaments(@Param("username") String username);

}
