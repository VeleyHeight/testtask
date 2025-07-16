package org.example.analyticpostservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.analyticpostservice.models.PostsReason;
import org.example.analyticpostservice.models.PostsStatus;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record AnalyticDTO(@NotNull Integer userId,
                          @NotBlank String title,
                          @NotNull PostsStatus status,
                          @NotNull PostsReason reason,
                          Instant createdAt,
                          @NotNull Set<String> bannedWords) {
}
