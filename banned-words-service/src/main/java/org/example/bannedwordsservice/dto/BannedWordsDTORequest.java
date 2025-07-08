package org.example.bannedwordsservice.dto;

import jakarta.validation.constraints.NotBlank;

public record BannedWordsDTORequest(@NotBlank String word){
}
