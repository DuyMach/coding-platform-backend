package com.codingplatform.coding_platform_backend.controller;

import com.codingplatform.coding_platform_backend.dto.TagDto;
import com.codingplatform.coding_platform_backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public ResponseEntity<Set<TagDto>> getAllTag(){
        Set<TagDto> tagDtoSet = tagService.getAllTag();

        return new ResponseEntity<>(tagDtoSet, HttpStatus.OK);
    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable("id") Long tagId){
        TagDto tagDto = tagService.getTagById(tagId);

        return new ResponseEntity<>(tagDto, HttpStatus.FOUND);
    }
}
