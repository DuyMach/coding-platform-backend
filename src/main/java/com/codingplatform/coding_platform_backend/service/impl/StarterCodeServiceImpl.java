package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.StarterCodeDto;
import com.codingplatform.coding_platform_backend.dto.mapper.StarterCodeMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.StarterCode;
import com.codingplatform.coding_platform_backend.models.enums.LanguageName;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.StarterCodeRepository;
import com.codingplatform.coding_platform_backend.service.StarterCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StarterCodeServiceImpl implements StarterCodeService {
    private StarterCodeRepository starterCodeRepository;
    private ProblemRepository problemRepository;

    @Autowired
    public StarterCodeServiceImpl(StarterCodeRepository starterCodeRepository, ProblemRepository problemRepository) {
        this.starterCodeRepository = starterCodeRepository;
        this.problemRepository = problemRepository;
    }

    @Override
    public StarterCodeDto createStarterCodeByProblemId(Long problemId, StarterCodeDto starterCodeDto) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemNotFoundException("Problem not found (ID) " + problemId));

        if(starterCodeRepository.findByProblemIdAndLanguage(problemId, starterCodeDto.getLanguage()).isPresent()){
            // Will add custom exception with global handling later
            throw new IllegalArgumentException("This problem (ID) " + problemId + " already have " + starterCodeDto.getLanguage() + " Starter Code");
        }

        StarterCode starterCode = StarterCodeMapper.mapToEntity(starterCodeDto);
        starterCode.setProblem(problem);

        StarterCode savedStarterCode = starterCodeRepository.save(starterCode);

        return StarterCodeMapper.mapToDto(savedStarterCode);
    }

    @Override
    public StarterCodeDto getStarterCodeByProblemIdAndLanguage(Long problemId, LanguageName language) {
        if (!problemRepository.existsById(problemId)){
            throw new ProblemNotFoundException("No StarterCode for non-existent problem (ID) " + problemId);
        }

        StarterCode starterCode = starterCodeRepository.findByProblemIdAndLanguage(problemId, language)
                .orElseThrow(() -> new IllegalArgumentException("Problem (ID) " + problemId + " does not support the " +
                        language + " language yet!"));

        return StarterCodeMapper.mapToDto(starterCode);
    }

    @Override
    public Set<StarterCodeDto> getAllStarterCodesByProblemId(Long problemId) {
        if (!problemRepository.existsById(problemId)){
            throw new ProblemNotFoundException("No StarterCode for non-existent problem (ID) " + problemId);
        }

        Set<StarterCode> starterCodes = starterCodeRepository.findAllByProblemId(problemId);

        return StarterCodeMapper.mapToDtoSet(starterCodes);
    }
}
