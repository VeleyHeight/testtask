package org.example.postservice.dto;

import jakarta.validation.constraints.NotBlank;

public record PostsDTORequest(@NotBlank String title, @NotBlank String content) {
}
