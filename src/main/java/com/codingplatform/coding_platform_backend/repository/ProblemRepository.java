package com.codingplatform.coding_platform_backend.repository;

import com.codingplatform.coding_platform_backend.models.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    boolean existsByTitle(String title);
}
