package com.profounddistortion.puzzle.repository;

import com.profounddistortion.puzzle.model.ApplicationUser;
import com.profounddistortion.puzzle.model.UserSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

public interface UserRepository extends CrudRepository<ApplicationUser, Integer> {
	String GET_USER_SUMMARY_INFO = "" +
		"  SELECT count(1) + 1 AS puzzle_num, sum(max_score) AS total_possible_score, sum(earned_score) AS current_score " +
		"    FROM puzzle " +
		"   WHERE complete_time IS NOT NULL AND user_id = :userId";

	@Query(value=GET_USER_SUMMARY_INFO, nativeQuery=true)
	Object getUserSummaryInfo(@Param("userId") Integer userId);

	ApplicationUser findByName(String name);


}
