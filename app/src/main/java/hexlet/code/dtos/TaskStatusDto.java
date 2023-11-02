package hexlet.code.dtos;

import hexlet.code.models.TaskStatus;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record TaskStatusDto(
    Long id,
    @NotBlank String name,
    @Hidden Date createdAt
) {

    public TaskStatusDto(TaskStatus status) {
        this(status.getId(), status.getName(), status.getCreatedAt());
    }
}
