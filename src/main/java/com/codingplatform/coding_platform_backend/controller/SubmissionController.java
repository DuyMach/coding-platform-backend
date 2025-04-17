package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.SubmissionRequestDto;
import com.codingplatform.coding_platform_backend.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
