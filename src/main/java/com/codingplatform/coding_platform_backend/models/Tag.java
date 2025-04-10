package com.codingplatform.coding_platform_backend.models;

import com.codingplatform.coding_platform_backend.models.enums.TagName;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TagName name;

    @ManyToMany(mappedBy = "tags")
    private Set<Problem> problems = new HashSet<>();
}
