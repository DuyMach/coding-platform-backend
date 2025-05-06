package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.StarterCodeDto;
import com.codingplatform.coding_platform_backend.models.enums.LanguageName;

import java.util.Set;

public interface StarterCodeService {
    StarterCodeDto createStarterCodeByProblemId(Long problemId, StarterCodeDto starterCodeDto);
    StarterCodeDto getStarterCodeByProblemIdAndLanguage(Long problemId, LanguageName language);
    Set<StarterCodeDto> getAllStarterCodesByProblemId(Long problemId);
}
