package com.socialbetting.repository;

import org.springframework.data.repository.CrudRepository;

import com.socialbetting.objectmodel.Competition;

public interface CompetitionRepository extends CrudRepository<Competition, Long> {

}
