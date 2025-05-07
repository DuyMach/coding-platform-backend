package com.codingplatform.coding_platform_backend.repository;

import com.codingplatform.coding_platform_backend.models.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}
