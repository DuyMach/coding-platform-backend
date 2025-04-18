package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.SubmissionDetailsDto;
import com.codingplatform.coding_platform_backend.dto.SubmissionRequestDto;
import com.codingplatform.coding_platform_backend.dto.SubmissionSummaryDto;
import com.codingplatform.coding_platform_backend.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class SubmissionController {
    private SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/submission")
    public ResponseEntity<SubmissionRequestDto> createSubmission(@RequestBody SubmissionRequestDto submissionRequestDto){
        SubmissionRequestDto savedSubmission = submissionService.createSubmission(submissionRequestDto);

        return new ResponseEntity<>(savedSubmission, HttpStatus.CREATED);
    }

    @GetMapping("/submissions")
    public ResponseEntity<Set<SubmissionSummaryDto>> getAllSubmissionsByUserIdAndProblemId(
            @RequestParam Long userId,
            @RequestParam Long problemId)
    {
        Set<SubmissionSummaryDto> submissionSet = submissionService.getAllSubmissionByUserIdAndProblemId(userId, problemId);

        return new ResponseEntity<>(submissionSet, HttpStatus.OK);
    }

    @GetMapping("/submission/{id}")
    public ResponseEntity<SubmissionDetailsDto> getSubmissionById(@PathVariable("id") Long submissionId){
        SubmissionDetailsDto submissionDetailsDto = submissionService.getSubmissionById(submissionId);

        return new ResponseEntity<>(submissionDetailsDto, HttpStatus.FOUND);
    }
}
