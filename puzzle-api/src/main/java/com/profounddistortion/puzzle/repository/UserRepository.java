package com.profounddistortion.puzzle.repository;

import org.springframework.data.repository.CrudRepository;

import com.profounddistortion.puzzle.model.ApplicationUser;

public interface UserRepository extends CrudRepository<ApplicationUser, Integer> {

	ApplicationUser findByName(String name);
}
