package com.codingplatform.coding_platform_backend.repository;

import com.codingplatform.coding_platform_backend.models.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    Set<TestCase> findAllByProblemId(Long problemId);
}
