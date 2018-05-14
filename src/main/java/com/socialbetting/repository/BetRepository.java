package com.socialbetting.repository;

import org.springframework.data.repository.CrudRepository;

import com.socialbetting.objectmodel.Bet;

public interface BetRepository extends CrudRepository<Bet, Long> {

}
