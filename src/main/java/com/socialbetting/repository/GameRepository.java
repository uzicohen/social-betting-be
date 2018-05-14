package com.socialbetting.repository;

import org.springframework.data.repository.CrudRepository;

import com.socialbetting.objectmodel.Game;

public interface GameRepository extends CrudRepository<Game, Long> {

}
