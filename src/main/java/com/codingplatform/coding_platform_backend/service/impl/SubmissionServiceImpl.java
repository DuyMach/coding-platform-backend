package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.SubmissionDetailsDto;
import com.codingplatform.coding_platform_backend.dto.SubmissionRequestDto;
import com.codingplatform.coding_platform_backend.dto.SubmissionSummaryDto;
import com.codingplatform.coding_platform_backend.dto.mapper.SubmissionDetailsMapper;
import com.codingplatform.coding_platform_backend.dto.mapper.SubmissionRequestMapper;
import com.codingplatform.coding_platform_backend.dto.mapper.SubmissionSummaryMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.exception.SubmissionNotFoundException;
import com.codingplatform.coding_platform_backend.exception.UserNotFoundException;
import com.codingplatform.coding_platform_backend.models.Problem;
import com.codingplatform.coding_platform_backend.models.Submission;
import com.codingplatform.coding_platform_backend.models.UserEntity;
import com.codingplatform.coding_platform_backend.models.enums.StatusName;
import com.codingplatform.coding_platform_backend.repository.ProblemRepository;
import com.codingplatform.coding_platform_backend.repository.SubmissionRepository;
import com.codingplatform.coding_platform_backend.repository.UserRepository;
import com.codingplatform.coding_platform_backend.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private SubmissionRepository submissionRepository;
    private UserRepository userRepository;
    private ProblemRepository problemRepository;


    @Autowired
    public SubmissionServiceImpl(SubmissionRepository submissionRepository, UserRepository userRepository, ProblemRepository problemRepository) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
    }

    @Override
    public SubmissionRequestDto createSubmission(SubmissionRequestDto submissionRequestDto) {
        if (submissionRequestDto.getUserId() == null && submissionRequestDto.getProblemId() == null){
            throw new IllegalArgumentException("UserId and ProblemId cannot be null!");
        }

        UserEntity user = userRepository.findById(submissionRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User does not exist!"));

        Problem problem = problemRepository.findById(submissionRequestDto.getProblemId())
                .orElseThrow(() -> new ProblemNotFoundException("Problem with given ID doesn't exist"));

        Submission submission = SubmissionRequestMapper.mapToSubmission(submissionRequestDto);
        submission.setUser(user);
        submission.setProblem(problem);
        submission.setStatus(StatusName.ATTEMPTING);


        Submission savedSubmission = submissionRepository.save(submission);


        return SubmissionRequestMapper.mapToSubmissionDto(savedSubmission);
    }

    @Override
    public Set<SubmissionSummaryDto> getAllSubmissionByUserIdAndProblemId(Long userId, Long problemId) {

        List<Submission> submissionList = submissionRepository.findByUserIdAndProblemId(userId, problemId);

        return SubmissionSummaryMapper.mapToSubmissionSummaryDtoSet(submissionList);
    }

    @Override
    public SubmissionDetailsDto getSubmissionById(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException("Submission with given ID doesn't exist"));

        return SubmissionDetailsMapper.mapToSubmissionDetailsDto(submission);
    }
}