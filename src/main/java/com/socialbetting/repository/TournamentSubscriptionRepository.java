package com.socialbetting.repository;

import org.springframework.data.repository.CrudRepository;

import com.socialbetting.objectmodel.TournamentSubscription;

public interface TournamentSubscriptionRepository extends CrudRepository<TournamentSubscription, Long> {

}
