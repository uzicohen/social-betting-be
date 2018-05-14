package com.socialbetting.repository;

import org.springframework.data.repository.CrudRepository;

import com.socialbetting.objectmodel.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {

}
