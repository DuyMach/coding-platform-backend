package com.codingplatform.coding_platform_backend.dto.mapper;

import com.codingplatform.coding_platform_backend.dto.TagDto;
import com.codingplatform.coding_platform_backend.models.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TagMapper {
    public static TagDto mapToTagDto(Tag tag){
        return new TagDto(
                tag.getId(),
                tag.getName()
        );
    }

    public static Set<TagDto> mapToTagDtoSet(List<Tag> tagList){
        return tagList.stream().map(TagMapper::mapToTagDto).collect(Collectors.toSet());
    }

    public static Set<TagDto> mapToTagDtoSet(Set<Tag> tagList){
        return tagList.stream().map(TagMapper::mapToTagDto).collect(Collectors.toSet());
    }

}
