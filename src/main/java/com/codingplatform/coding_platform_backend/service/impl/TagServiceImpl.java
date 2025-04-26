package com.codingplatform.coding_platform_backend.service.impl;

import com.codingplatform.coding_platform_backend.dto.TagDto;
import com.codingplatform.coding_platform_backend.dto.mapper.TagMapper;
import com.codingplatform.coding_platform_backend.exception.TagAlreadyExistException;
import com.codingplatform.coding_platform_backend.exception.TagNotFoundException;
import com.codingplatform.coding_platform_backend.models.Tag;
import com.codingplatform.coding_platform_backend.models.enums.TagName;
import com.codingplatform.coding_platform_backend.repository.TagRepository;
import com.codingplatform.coding_platform_backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<TagDto> getAllTag() {
        List<Tag> tagList = tagRepository.findAll();
        return TagMapper.mapToTagDtoSet(tagList);
    }

    @Override
    public TagDto getTagById(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag with given ID doesn't exist!"));

        return TagMapper.mapToTagDto(tag);
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        if (tagRepository.existsByName(tagDto.getName())){
            throw new TagAlreadyExistException("This Tag name already exist in the database!");
        }

        Tag tag = TagMapper.mapToTag(tagDto);
        Tag savedTag = tagRepository.save(tag);

        return TagMapper.mapToTagDto(savedTag);
    }
}
