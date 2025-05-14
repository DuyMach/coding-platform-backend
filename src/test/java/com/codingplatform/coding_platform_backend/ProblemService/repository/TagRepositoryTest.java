package com.codingplatform.coding_platform_backend.ProblemService.repository;

import com.codingplatform.coding_platform_backend.models.Tag;
import com.codingplatform.coding_platform_backend.models.enums.TagName;
import com.codingplatform.coding_platform_backend.repository.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TagRepositoryTest {
    private final TagRepository tagRepository;

    @Autowired
    public TagRepositoryTest(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Test
    public void TagRepository_Save_ReturnsSavedTag(){
        // Arrange
        Tag arrayTag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();
        // Act
        Tag savedTag = tagRepository.save(arrayTag);

        // Assert
        Assertions.assertThat(savedTag).isNotNull();
        Assertions.assertThat(savedTag.getId()).isGreaterThan(0);
        Assertions.assertThat(savedTag.getName()).isEqualTo(TagName.ARRAYS);
    }

    @Test
    public void TagRepository_FindAll_ReturnsTagList(){
        // Arrange
        int expectedSize = 3;
        Tag arrayTag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();
        tagRepository.save(arrayTag);

        Tag designTag = Tag.builder()
                .name(TagName.DESIGN)
                .build();
        tagRepository.save(designTag);

        Tag heapTag = Tag.builder()
                .name(TagName.HEAP)
                .build();
        tagRepository.save(heapTag);

        // Act
        List<Tag> tags = tagRepository.findAll();

        // Assert
        Assertions.assertThat(tags).isNotNull().isNotEmpty();
        Assertions.assertThat(tags.size()).isEqualTo(expectedSize);
        Assertions.assertThat(tags).containsExactlyInAnyOrder(arrayTag, heapTag, designTag);
    }

    @Test
    public void TagRepository_FindById_ReturnsTag(){
        // Assign
        Tag arrayTag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();
        Tag savedTag = tagRepository.save(arrayTag);

        // Act
        Optional<Tag> result = tagRepository.findById(savedTag.getId());

        // Assert
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getName()).isEqualTo(TagName.ARRAYS);
    }

    @Test
    public void TagRepository_FindById_ReturnsEmptyOptional_WhenNotFound(){
        // Assign
        Tag arrayTag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();
        Tag savedTag = tagRepository.save(arrayTag);

        // Act
        Optional<Tag> result = tagRepository.findById(savedTag.getId() + 99);

        // Assert
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void TagRepository_ExistsByName_ReturnsTrue(){
        // Assign
        Tag arrayTag = Tag.builder()
                .name(TagName.ARRAYS)
                .build();
        tagRepository.save(arrayTag);

        // Act
        Boolean isPresent = tagRepository.existsByName(TagName.ARRAYS);

        // Assert
        Assertions.assertThat(isPresent).isTrue();
    }

    @Test
    public void TagRepository_ExistsByName_ReturnsFalse_WhenTagDoesNotExist(){
        // Act
        Boolean isPresent = tagRepository.existsByName(TagName.ARRAYS);

        // Assert
        Assertions.assertThat(isPresent).isFalse();
    }
}
