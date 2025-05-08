package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.WrapperTemplateDto;
import com.codingplatform.coding_platform_backend.dto.mapper.WrapperTemplateMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.WrapperTemplate;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.WrapperTemplateRepository;
import com.codingplatform.coding_platform_backend.service.WrapperTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WrapperTemplateServiceImpl implements WrapperTemplateService {
    private WrapperTemplateRepository wrapperTemplateRepository;
    private ProblemRepository problemRepository;

    @Autowired
    public WrapperTemplateServiceImpl(WrapperTemplateRepository wrapperTemplateRepository, ProblemRepository problemRepository) {
        this.wrapperTemplateRepository = wrapperTemplateRepository;
        this.problemRepository = problemRepository;
    }

    @Override
    public WrapperTemplateDto createWrapperTemplateByProblemId(Long problemId, WrapperTemplateDto wrapperTemplateDto) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemNotFoundException("Problem with given ID doesn't exist: " + problemId));

        // will implement custom exception later
        if (wrapperTemplateRepository.existsByProblemIdAndLanguage(problemId, wrapperTemplateDto.getLanguage())) {
            throw new IllegalArgumentException("A wrapper template already exists for this Problem and Language (ID, LANGUAGE) : " +
                    problemId + " " + wrapperTemplateDto.getLanguage());

        }

        WrapperTemplate wrapperTemplate = WrapperTemplateMapper.mapToEntity(wrapperTemplateDto);
        wrapperTemplate.setProblem(problem);

        WrapperTemplate savedWrapperTemplate = wrapperTemplateRepository.save(wrapperTemplate);

        return WrapperTemplateMapper.mapToDto(savedWrapperTemplate);
    }
}
