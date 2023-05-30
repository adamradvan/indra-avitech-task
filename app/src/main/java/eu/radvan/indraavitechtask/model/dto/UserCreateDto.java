package eu.radvan.indraavitechtask.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(@NotBlank String name) {
}
