package org.example.postservice.dto;

import java.time.Instant;

public record UsersPostDTO(String title, String content, Instant createdAt) {
}