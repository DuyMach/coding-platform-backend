package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.*;
import com.codingplatform.coding_platform_backend.dto.mapper.SubmissionDetailsMapper;
import com.codingplatform.coding_platform_backend.dto.mapper.SubmissionRequestMapper;
import com.codingplatform.coding_platform_backend.dto.mapper.SubmissionSummaryMapper;
import com.codingplatform.coding_platform_backend.exception.ProblemNotFoundException;
import com.codingplatform.coding_platform_backend.exception.SubmissionNotFoundException;
import com.codingplatform.coding_platform_backend.exception.UserNotFoundException;
import com.codingplatform.coding_platform_backend.models.*;
import com.codingplatform.coding_platform_backend.models.enums.StatusName;
import com.codingplatform.coding_platform_backend.repository.*;
import com.codingplatform.coding_platform_backend.service.Judge0Client;
import com.codingplatform.coding_platform_backend.service.SubmissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private SubmissionRepository submissionRepository;
    private UserRepository userRepository;
    private ProblemRepository problemRepository;
    private TestCaseRepository testCaseRepository;
    private WrapperTemplateRepository wrapperTemplateRepository;
    private SubmissionResultRepository submissionResultRepository;
    private Judge0Client judge0Client;

    @Autowired
    public SubmissionServiceImpl(
            SubmissionRepository submissionRepository,
            UserRepository userRepository,
            ProblemRepository problemRepository,
            TestCaseRepository testCaseRepository,
            WrapperTemplateRepository wrapperTemplateRepository,
            SubmissionResultRepository submissionResultRepository,
            Judge0Client judge0Client
    ) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
        this.testCaseRepository = testCaseRepository;
        this.wrapperTemplateRepository = wrapperTemplateRepository;
        this.submissionResultRepository = submissionResultRepository;
        this.judge0Client = judge0Client;
    }

    @Override
    public SubmissionDetailsDto createSubmission(SubmissionRequestDto submissionRequestDto) {
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
        submission.setStatus(StatusName.PENDING);

        Submission savedSubmission = submissionRepository.save(submission);

        Set<TestCase> testCases = testCaseRepository.findAllByProblemId(problem.getId());
        WrapperTemplate wrapperTemplate = wrapperTemplateRepository.findByProblemIdAndLanguage(problem.getId(), submission.getLanguage())
                .orElseThrow(() -> new IllegalArgumentException("There is no support for this language yet!"));

        for (TestCase testCase : testCases){
            String wrappedCode = wrapCode(wrapperTemplate.getCode(), submission.getCode(), testCase.getInput());

            Judge0SubmissionRequest judgeRequest = Judge0SubmissionRequest.builder()
                    // Only supporting JAVA for now, will create a map when implementing support for multiple languages
                    .languageId(62)
                    .sourceCode(wrappedCode)
                    // Option to send input if I decided to improve my implementation later down the road
                    .stdin("")
                    .expectedOutput(testCase.getExpectedOutput())
                    .build();

            Judge0SubmissionResponse response = judge0Client.submitCode(judgeRequest);

            boolean passed = response.getStatus().getId() == 3 &&
                    response.getStdout() != null &&
                    response.getStdout().trim().equals(testCase.getExpectedOutput().trim());

            SubmissionResult submissionResult = SubmissionResult.builder()
                    .submission(savedSubmission)
                    .testCase(testCase)
                    .actualOutput(response.getStdout())
                    .errorMessage(response.getCompileOutput())
                    .passed(passed)
                    .build();

            submissionResultRepository.save(submissionResult);

            if (!passed) {
                if(response.getStatus().getDescription().equals("Wrong Answer")){
                    String reasonMessage = "Wrong Answer!";
                    String testInputMessage = "\nTest Case: " + testCase.getInput();
                    String expectedOutputMessage = "\nExpected output: " + testCase.getExpectedOutput();
                    String actualOutputMessage = "\nActual output: " + response.getStdout();
                    savedSubmission.setOutput(reasonMessage + testInputMessage + expectedOutputMessage + actualOutputMessage);
                    savedSubmission.setStatus(StatusName.WRONG_ANSWER);
                } else if (response.getStatus().getDescription().equals("Compilation Error")){
                    String reasonMessage = "Compilation Error\n";
                    savedSubmission.setOutput(reasonMessage + submissionResult.getErrorMessage());
                    savedSubmission.setStatus(StatusName.COMPILE_ERROR);
                } else {
                    savedSubmission.setOutput(submissionResult.getErrorMessage());
                    savedSubmission.setStatus(StatusName.INTERNAL_ERROR);
                }
                submissionRepository.save(savedSubmission);
                return SubmissionDetailsMapper.mapToSubmissionDetailsDto(savedSubmission);
            }
        }

        savedSubmission.setStatus(StatusName.ACCEPTED);

        savedSubmission.setOutput("All " + testCases.size() +" tests passed!");
        submissionRepository.save(savedSubmission);
        return SubmissionDetailsMapper.mapToSubmissionDetailsDto(submission);
    }

    private String wrapCode(String wrapperTemplate, String userCode, String testInputExpression){
        String wrappedMain = wrapperTemplate.replace("{{TEST_INPUT_EXPRESSION}}", testInputExpression);

        return userCode+ "\n\n" + wrappedMain;
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