package org.example.postservice.dto;

import org.example.postservice.models.PostsReason;
import org.example.postservice.models.PostsStatus;

import java.time.Instant;
import java.util.Set;

public record AnalyticDTO(Integer userId,
                          String title,
                          PostsStatus status,
                          PostsReason reason,
                          Instant createdAt,
                          Set<String> bannedWords) {
}