package org.example.analyticpostservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AnalyticPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String title;
    @Enumerated(EnumType.STRING)
    private PostsStatus status;
    @Enumerated(EnumType.STRING)
    private PostsReason reason;
    @CreationTimestamp
    private Instant createdAt;
    @ElementCollection
    private Set<String> bannedWords;
}