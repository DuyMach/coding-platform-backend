package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.AddTagsToProblemDto;
import com.codingplatform.coding_platform_backend.dto.ProblemDto;
import com.codingplatform.coding_platform_backend.dto.TagDto;
import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.TagName;
import com.codingplatform.coding_platform_backend.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class ProblemController {
    private ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("/problem")
    public ResponseEntity<ProblemDto> createProblem(@RequestBody ProblemDto problemDto){
        ProblemDto createdProblem = problemService.createProblem(problemDto);

        return new ResponseEntity<>(createdProblem, HttpStatus.CREATED);
    }

    @GetMapping("/problems")
    public ResponseEntity<Set<ProblemDto>> getAllProblems(
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) TagName tagName)

    {
        Set<ProblemDto> problemDtoSet;
        if(tagName != null && difficulty != null) {
            problemDtoSet = problemService.getAllProblemsByTagAndDifficulty(tagName, difficulty);
        } else if (tagName != null){
            problemDtoSet = problemService.getAllProblemsByTagName(tagName);
        } else if (difficulty != null){
            problemDtoSet = problemService.getAllProblemByDifficulty(difficulty);
        } else {
            problemDtoSet = problemService.getAllProblem();
        }
        return new ResponseEntity<>(problemDtoSet, HttpStatus.OK);
    }

    @GetMapping("/problems/{id}/tags")
    public ResponseEntity<Set<TagDto>> getAllTagByProblemId(@PathVariable("id") Long problemId){
        Set<TagDto> tagDtoSet = problemService.getAllTagByProblemId(problemId);

        return new ResponseEntity<>(tagDtoSet, HttpStatus.OK);
    }

    @PostMapping("/problems/{id}/tags")
    public ResponseEntity<String> addTagsToProblem(
            @PathVariable("id") Long problemId,
            @RequestBody AddTagsToProblemDto addTagsToProblemDto)
    {
        String message = problemService.addTagsToProblem(problemId, addTagsToProblemDto);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/problems/by-tag/{id}")
    public ResponseEntity<Set<ProblemDto>> getAllProblemByTagId(@PathVariable("id") Long tagId){
        Set<ProblemDto> problemDtoSet = problemService.getAllProblemByTagId(tagId);

        return new ResponseEntity<>(problemDtoSet, HttpStatus.OK);
    }

    @GetMapping("/problems/{id}")
    public ResponseEntity<ProblemDto> getProblemById(@PathVariable("id") Long problemId){
        ProblemDto problemDto = problemService.getProblemById(problemId);

        return new ResponseEntity<>(problemDto, HttpStatus.OK);
    }

}
