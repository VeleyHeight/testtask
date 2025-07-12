package org.example.bannedwordsservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CheckBannedWordsDTORequest(@NotBlank String title, @NotBlank String content){
}
