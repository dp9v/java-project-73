package hexlet.code.dtos;

import hexlet.code.models.Task;

import java.util.Date;

public record TaskDto(
    Long id,
    UserDto author,
    UserDto executor,
    TaskStatusDto taskStatus,
    String name,
    String description,
    Date createdAt
) {
    public TaskDto(Task task) {
        this(
            task.getId(),
            new UserDto(task.getAuthor()),
            new UserDto(task.getExecutor()),
            new TaskStatusDto(task.getTaskStatus()),
            task.getName(),
            task.getDescription(),
            task.getCreatedAt()
        );
    }
}
