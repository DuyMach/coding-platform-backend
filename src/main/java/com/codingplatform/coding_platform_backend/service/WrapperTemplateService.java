package com.codingplatform.coding_platform_backend.service;

import com.codingplatform.coding_platform_backend.dto.WrapperTemplateDto;
import com.codingplatform.coding_platform_backend.models.enums.LanguageName;

public interface WrapperTemplateService {
    WrapperTemplateDto createWrapperTemplateByProblemId(Long problemId, WrapperTemplateDto wrapperTemplateDto);
}
