package hexlet.code.dtos;

import jakarta.validation.constraints.NotBlank;

public record TaskCreatingDto(
    @NotBlank String name,
    String description,
    @NotBlank Long executorId,
    @NotBlank Long taskStatusId
) {
}
