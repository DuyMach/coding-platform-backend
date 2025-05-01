package com.codingplatform.coding_platform_backend.models;

import com.codingplatform.coding_platform_backend.models.enums.Difficulty;
import com.codingplatform.coding_platform_backend.models.enums.ProblemVisibility;
import com.codingplatform.coding_platform_backend.persistence.ConstraintListConverter;
import com.codingplatform.coding_platform_backend.persistence.HintListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "problem_tags",
            joinColumns = @JoinColumn(name = "problem_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TestCase> testCases = new HashSet<>();

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Submission> submissions = new HashSet<>();

    @Column(nullable = false)
    private String functionName;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = ConstraintListConverter.class)
    private List<String> constraints = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    @Convert(converter = HintListConverter.class)
    private List<String> hint = new ArrayList<>();

    @Column(nullable = false)
    private boolean isPremium;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemVisibility visibility;

    @Builder.Default
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StarterCode> starterCodes = new HashSet<>();

    public Problem(String title, String description, Difficulty difficulty) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
    }

    public boolean addTag(Tag tag){
        return tags.add(tag);
    }
}
