package hexlet.code.dtos;

import hexlet.code.models.TaskStatus;

import java.util.Date;

public record TaskStatusDto(
    String id,
    String name,
    Date createdAt
) {

    public TaskStatusDto(TaskStatus status) {
        this(status.getId().toString(), status.getName(), status.getCreatedAt());
    }
}
