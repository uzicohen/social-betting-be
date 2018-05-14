package com.socialbetting.repository;

import org.springframework.data.repository.CrudRepository;

import com.socialbetting.objectmodel.TournamentInstance;

public interface TournamentInstanceRepository extends CrudRepository<TournamentInstance, Long> {

}
