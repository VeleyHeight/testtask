package org.example.bannedwordsservice.dto;

import java.time.Instant;

public record BannedWordsDTOResponse(String word, Instant createdAt) {
}
