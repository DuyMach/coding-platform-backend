package com.codingplatform.coding_platform_backend.repository;

import com.codingplatform.coding_platform_backend.models.WrapperTemplate;
import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WrapperTemplateRepository extends JpaRepository<WrapperTemplate, Long> {
    boolean existsByProblemIdAndLanguage(Long problemId, LanguageName language);
    Optional<WrapperTemplate> findByProblemIdAndLanguage(Long problemId, LanguageName language);
}
