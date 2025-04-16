package com.codingplatform.coding_platform_backend.repository;

import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.Tag;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.TagName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    boolean existsByTitle(String title);
    List<Problem> findAllByDifficulty(Difficulty difficulty);

    @Query("SELECT p FROM Problem p JOIN p.tags t WHERE t.name = :tagName")
    List<Problem> findByTagName(@Param("tagName") TagName tagName);

    @Query("SELECT DISTINCT p FROM Problem p JOIN p.tags t " +
            "WHERE t.name = :tagName AND p.difficulty = :difficulty")
    List<Problem> findByTagNameAndDifficulty(@Param("tagName") TagName tagName, @Param("difficulty") Difficulty difficulty);
}
