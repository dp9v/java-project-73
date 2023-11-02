package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.dtos.TaskDto;
import hexlet.code.models.Task;
import hexlet.code.models.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static hexlet.code.Common.DEFAULT_EMAIL;
import static hexlet.code.Common.fromJson;
import static hexlet.code.controllers.TaskController.CONTROLLER_PATH;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerIT extends BaseIT {
    private final static String DEFAULT_TASK_NAME = "taskName";
    private final static String DEFAULT_TASK_DESCRIPTION = "taskDescription";

    @SneakyThrows
    @Test
    public void testGetAll() {
        var user = userRepository.findByEmail(DEFAULT_EMAIL).orElseThrow();
        var createdTasks = List.of(
            buildTask(user),
            buildTask("task2", "desc2", user)
        );
        taskRepository.saveAll(createdTasks);

        var response = performByUser(get(CONTROLLER_PATH), DEFAULT_EMAIL)
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        List<TaskDto> result = fromJson(response, new TypeReference<>() {
        });
        assertThat(result).hasSize(2);
    }

    @SneakyThrows
    @Test
    public void testGet() {
        var user = userRepository.findByEmail(DEFAULT_EMAIL).orElseThrow();
        Task createdTask = taskRepository.save(buildTask(user));
        var response = performByUser(get(CONTROLLER_PATH + "/" + createdTask.getId()), DEFAULT_EMAIL)
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        TaskDto result = fromJson(response, new TypeReference<>() {});
        assertThat(result.id()).isEqualTo(createdTask.getId());
        assertThat(result.name()).isEqualTo(DEFAULT_TASK_NAME);
        assertThat(result.description()).isEqualTo(DEFAULT_TASK_DESCRIPTION);
        assertThat(result.author().id()).isEqualTo(user.getId());
        assertThat(result.executor().id()).isEqualTo(user.getId());
    }


    private Task buildTask(User user) {
        return buildTask(DEFAULT_TASK_NAME, DEFAULT_TASK_DESCRIPTION, user);
    }

    private Task buildTask(String name, String description, User user) {
        return new Task()
            .setAuthor(user)
            .setExecutor(user)
            .setName(name)
            .setDescription(description);
    }
}
