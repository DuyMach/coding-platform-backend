package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.*;
import com.codingplatform.coding_platform_backend.dto.mapper.ProblemMapper;
import com.codingplatform.coding_platform_backend.dto.mapper.TagMapper;
import com.codingplatform.coding_platform_backend.dto.mapper.UpdateProblemMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemAlreadyExistException;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.exception.TagNotFoundException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.Tag;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.TagName;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.TagRepository;
import com.codingplatform.coding_platform_backend.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ProblemServiceImpl implements ProblemService {
    private ProblemRepository problemRepository;
    private TagRepository tagRepository;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, TagRepository tagRepository) {
        this.problemRepository = problemRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public ProblemDto createProblem(ProblemDto problemDto) {
        if(problemRepository.existsByTitle(problemDto.getTitle())){
            throw new ProblemAlreadyExistException("This title is already taken!");
        }

        Problem problem = ProblemMapper.mapToProblem(problemDto);
        problem.setUpdatedAt(LocalDateTime.now());
        Problem savedProblem = problemRepository.save(problem);

        return ProblemMapper.mapToProblemDto(savedProblem);
    }

    @Override
    public Set<ProblemDto> getAllProblem() {
        List<Problem> problemList = problemRepository.findAll();

        return ProblemMapper.mapToProblemDtoSet(problemList);
    }

    @Override
    public Set<TagDto> getAllTagByProblemId(Long problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemNotFoundException("Problem with given ID doesn't exist"));

        Set<Tag> tagSet = problem.getTags();
        return TagMapper.mapToTagDtoSet(tagSet);
    }

    @Override
    public String addTagsToProblem(Long problemId, AddTagsToProblemDto addTagsToProblemDto) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemNotFoundException("Problem with given ID doesn't exist"));

        Set<Long> tagIdSet = addTagsToProblemDto.getTagIdSet();

        if (tagIdSet == null || tagIdSet.isEmpty()){
            throw new IllegalArgumentException("Please include at least one tag!");
        }

        List<Tag> tagList = tagRepository.findAllById(tagIdSet);

        if(tagIdSet.size() != tagList.size()){
            throw new TagNotFoundException("Tag with given ID doesn't exist!");
        }

        tagList.forEach(problem::addTag);
        problemRepository.save(problem);

        return "Tags successfully added to problem";
    }

    @Override
    public Set<ProblemDto> getAllProblemByTagId(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag with given ID doesn't exist!"));

        Set<Problem> problemSet = tag.getProblems();

        return ProblemMapper.mapToProblemDtoSet(problemSet.stream().toList());
    }

    @Override
    public ProblemDto getProblemById(Long problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemNotFoundException("Problem with given ID doesn't exist"));

        return ProblemMapper.mapToProblemDto(problem);
    }

    @Override
    public Set<ProblemDto> getAllProblemByDifficulty(Difficulty difficulty) {
        List<Problem> problemList = problemRepository.findAllByDifficulty(difficulty);

        return ProblemMapper.mapToProblemDtoSet(problemList);
    }

    @Override
    public Set<ProblemDto> getAllProblemsByTagName(TagName tagName) {
        List<Problem> problemList = problemRepository.findByTagName(tagName);

        return ProblemMapper.mapToProblemDtoSet(problemList);
    }

    @Override
    public Set<ProblemDto> getAllProblemsByTagAndDifficulty(TagName tagName, Difficulty difficulty) {
        List<Problem> problemList = problemRepository.findByTagNameAndDifficulty(tagName, difficulty);

        return ProblemMapper.mapToProblemDtoSet(problemList);
    }

    @Override
    public UpdateProblemDto updateProblem(Long problemId, UpdateProblemDto updateProblemDto) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemNotFoundException("Problem with given ID doesn't exist"));

        Problem updatedProblem = UpdateProblemMapper.applyUpdates(problem, updateProblemDto);
        updatedProblem.setUpdatedAt(LocalDateTime.now());

        Problem savedProblem = problemRepository.save(updatedProblem);

        return UpdateProblemMapper.mapToDto(savedProblem);
    }

    @Override
    public ProblemResponse getAllProblemPageable(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Problem> problems = problemRepository.findAll(pageable);
        List<Problem> problemList = problems.getContent();
        Set<ProblemDto> content = ProblemMapper.mapToProblemDtoSet(problemList);

        return ProblemResponse.builder()
                .content(content.stream().toList())
                .pageNo(problems.getNumber())
                .pageSize(problems.getSize())
                .totalElements(problems.getTotalElements())
                .totalPages(problems.getTotalPages())
                .isLastPage(problems.isLast())
                .build();
    }

    @Override
    public ProblemResponse getAllProblemByDifficultyPageable(Difficulty difficulty, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Problem> problemPage = problemRepository.findAllByDifficulty(difficulty, pageable);
        List<Problem> problemList = problemPage.getContent();
        List<ProblemDto> content = ProblemMapper.mapToProblemDtoSet(problemList).stream().toList();



        return ProblemResponse.builder()
                .content(content.stream().toList())
                .pageNo(problemPage.getNumber())
                .pageSize(problemPage.getSize())
                .totalElements(problemPage.getTotalElements())
                .totalPages(problemPage.getTotalPages())
                .isLastPage(problemPage.isLast())
                .build();
    }

    @Override
    public ProblemResponse getAllProblemsByTagNamePageable(TagName tagName, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Problem> problemPage = problemRepository.findByTagName(tagName, pageable);
        List<Problem> problemList = problemPage.getContent();
        List<ProblemDto> content = ProblemMapper.mapToProblemDtoSet(problemList).stream().toList();

        return ProblemResponse.builder()
                .content(content.stream().toList())
                .pageNo(problemPage.getNumber())
                .pageSize(problemPage.getSize())
                .totalElements(problemPage.getTotalElements())
                .totalPages(problemPage.getTotalPages())
                .isLastPage(problemPage.isLast())
                .build();
    }

    @Override
    public ProblemResponse getAllProblemsByTagAndDifficultyPageable(TagName tagName, Difficulty difficulty, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Problem> problemPage = problemRepository.findByTagNameAndDifficulty(tagName, difficulty, pageable);
        List<Problem> problemList = problemPage.getContent();
        List<ProblemDto> content = ProblemMapper.mapToProblemDtoSet(problemList).stream().toList();

        return ProblemResponse.builder()
                .content(content.stream().toList())
                .pageNo(problemPage.getNumber())
                .pageSize(problemPage.getSize())
                .totalElements(problemPage.getTotalElements())
                .totalPages(problemPage.getTotalPages())
                .isLastPage(problemPage.isLast())
                .build();
    }
}