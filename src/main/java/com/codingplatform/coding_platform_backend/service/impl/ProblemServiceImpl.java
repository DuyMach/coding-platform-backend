package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.ProblemDto;
import com.codingplatform.coding_platform_backend.dto.mapper.ProblemMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemAlreadyExistException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProblemServiceImpl implements ProblemService {
    private ProblemRepository problemRepository;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Override
    public ProblemDto createProblem(ProblemDto problemDto) {
        if(problemRepository.existsByTitle(problemDto.getTitle())){
            throw new ProblemAlreadyExistException("This title is already taken!");
        }

        Problem problem = ProblemMapper.mapToProblem(problemDto);
        Problem savedProblem = problemRepository.save(problem);

        return ProblemMapper.mapToProblemDto(savedProblem);
    }

    @Override
    public Set<ProblemDto> getAllProblem() {
        List<Problem> problemList = problemRepository.findAll();

        return ProblemMapper.mapToProblemDtoSet(problemList);
    }
}
