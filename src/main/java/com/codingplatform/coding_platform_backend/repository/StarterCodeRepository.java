package com.codingplatform.coding_platform_backend.repository;

import com.codingplatform.coding_platform_backend.models.StarterCode;
import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StarterCodeRepository extends JpaRepository<StarterCode, Long> {
    Optional<StarterCode> findByProblemIdAndLanguage(Long problemId, LanguageName language);
}
