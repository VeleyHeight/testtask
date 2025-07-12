package org.example.postservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int userId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostsStatus status;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Instant createdAt;
}
