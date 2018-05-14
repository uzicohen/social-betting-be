package com.socialbetting.repository;

import org.springframework.data.repository.CrudRepository;

import com.socialbetting.objectmodel.Tournament;

public interface TournamentRepository extends CrudRepository<Tournament, Long> {

}
