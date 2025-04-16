package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.repository.SubmissionRepository;
import com.codingplatform.coding_platform_backend.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }
}
