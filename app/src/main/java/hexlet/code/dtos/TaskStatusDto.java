package hexlet.code.dtos;

import hexlet.code.models.TaskStatus;

import java.util.Date;

public record TaskStatusDto(
    Long id,
    String name,
    Date createdAt
) {

    public TaskStatusDto(TaskStatus status) {
        this(status.getId(), status.getName(), status.getCreatedAt());
    }
}
