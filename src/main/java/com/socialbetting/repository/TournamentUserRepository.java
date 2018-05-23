package com.socialbetting.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostFilter;

import com.socialbetting.objectmodel.TournamentUser;

public interface TournamentUserRepository extends CrudRepository<TournamentUser, Long> {
	@PostFilter("filterObject.user.getId() == principal.id")
	@Override
	Iterable<TournamentUser> findAll();
}
