package com.codingplatform.coding_platform_backend.ProblemService.service;

import com.codingplatform.coding_platform_backend.dto.TagDto;
import com.codingplatform.coding_platform_backend.exception.TagAlreadyExistException;
import com.codingplatform.coding_platform_backend.exception.TagNotFoundException;
import com.codingplatform.coding_platform_backend.models.Tag;
import com.codingplatform.coding_platform_backend.models.enums.TagName;
import com.codingplatform.coding_platform_backend.repository.TagRepository;
import com.codingplatform.coding_platform_backend.service.impl.TagServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    public void TagService_GetAllTag_ReturnsTagDtoSet(){
        // Arrange
        int expectedSize = 3;
        Tag arrayTag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();

        Tag designTag = Tag.builder()
                .name(TagName.DESIGN)
                .build();

        Tag heapTag = Tag.builder()
                .name(TagName.HEAP)
                .build();

        TagDto arrayTagDto = TagDto.builder().name(TagName.ARRAYS).build();
        TagDto designTagDto = TagDto.builder().name(TagName.DESIGN).build();
        TagDto heapTagDto = TagDto.builder().name(TagName.HEAP).build();

        when(tagRepository.findAll()).thenReturn(Arrays.asList(arrayTag, designTag, heapTag));

        // Act
        Set<TagDto> tagDtoSet = tagService.getAllTag();

        // Assert
        Assertions.assertThat(tagDtoSet).isNotNull().isNotEmpty();
        Assertions.assertThat(tagDtoSet.size()).isEqualTo(expectedSize);
        Assertions.assertThat(tagDtoSet).containsExactlyInAnyOrder(heapTagDto, designTagDto, arrayTagDto);
    }

    @Test
    public void TagService_GetTagById_ReturnsTagDto(){
        // Arrange
        Long tagId = 1L;
        Tag arrayTag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();

        when(tagRepository.findById(tagId)).thenReturn(Optional.of(arrayTag));

        // Act
        TagDto result = tagService.getTagById(tagId);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(arrayTag.getName());
    }

    @Test
    public void TagService_GetTagById_ThrowsTagNotFoundException_WhenTagNotFound(){
        // Arrange
        Long tagId = 1L;

        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThatThrownBy(() -> tagService.getTagById(tagId))
                .isInstanceOf(TagNotFoundException.class)
                .hasMessage("Tag with given ID doesn't exist!");
    }

    @Test
    public void TagService_CreateTag_ReturnsTagDto(){
        // Arrange
        Tag arrayTag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();

        TagDto arrayTagDto = TagDto.builder().name(TagName.ARRAYS).build();

        when(tagRepository.existsByName(arrayTag.getName())).thenReturn(false);
        when(tagRepository.save(Mockito.any(Tag.class))).thenReturn(arrayTag);

        // Act
        TagDto result = tagService.createTag(arrayTagDto);

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(arrayTag.getName());
    }

    @Test
    public void TagService_CreateTag_ThrowsTagAlreadyExistException_WhenTagExistsByName(){
        // Arrange
        TagDto arrayTagDto = TagDto.builder().name(TagName.ARRAYS).build();

        when(tagRepository.existsByName(arrayTagDto.getName())).thenReturn(true);

        // Act and Assert
        Assertions.assertThatThrownBy(() -> tagService.createTag(arrayTagDto))
                .isInstanceOf(TagAlreadyExistException.class)
                .hasMessage("This Tag name already exist in the database!");
    }
}
