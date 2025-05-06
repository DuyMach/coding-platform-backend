package com.codingplatform.coding_platform_backend.repository;

import com.codingplatform.coding_platform_backend.models.StarterCode;
import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface StarterCodeRepository extends JpaRepository<StarterCode, Long> {
    Optional<StarterCode> findByProblemIdAndLanguage(Long problemId, LanguageName language);
    Set<StarterCode> findAllByProblemId(Long problemId);
}
