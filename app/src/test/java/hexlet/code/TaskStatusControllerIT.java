package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.controllers.TaskStatusController;
import hexlet.code.dtos.TaskStatusDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static hexlet.code.Common.DEFAULT_EMAIL;
import static hexlet.code.Common.STATUS_LABEL;
import static hexlet.code.Common.asJson;
import static hexlet.code.Common.fromJson;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskStatusControllerIT extends BaseIT {

    @Test
    @SneakyThrows
    public void testCreatingTaskStatus() {
        createStatus(STATUS_LABEL);
        var statuses = taskStatusRepository.findAll();
        assertThat(statuses).hasSize(1);
        assertThat(statuses.get(0).getName()).isEqualTo(STATUS_LABEL);
    }

    @Test
    @SneakyThrows
    public void testGet() {
        var createdStatus = createStatus(STATUS_LABEL);
        var response = performByUser(
            get(TaskStatusController.CONTROLLER_PATH + "/" + createdStatus.id()), DEFAULT_EMAIL
        )
            .andExpect(status().isOk())
            .andReturn().getResponse();
        TaskStatusDto taskStatusDto = fromJson(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(taskStatusDto.name()).isEqualTo(createdStatus.name());
        assertThat(taskStatusDto.createdAt()).isEqualTo(createdStatus.createdAt());
    }

    @Test
    @SneakyThrows
    public void testGetAll() {
        createStatus("label1");
        createStatus("label2");
        var response = performByUser(get(TaskStatusController.CONTROLLER_PATH), DEFAULT_EMAIL)
            .andExpect(status().isOk())
            .andReturn().getResponse();
        List<TaskStatusDto> taskStatuses = fromJson(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(taskStatuses).hasSize(2);
    }

    @Test
    @SneakyThrows
    public void testUpdate() {
        var createdTaskStatus = createStatus("label");
        performByUser(
            put(TaskStatusController.CONTROLLER_PATH + "/" + createdTaskStatus.id())
                .content(asJson(new TaskStatusDto(null, STATUS_LABEL, createdTaskStatus.createdAt())))
                .contentType(MediaType.APPLICATION_JSON),
            DEFAULT_EMAIL
        ).andExpect(status().isOk());
        var updatesTaskStatus = taskStatusRepository.findAll().get(0);
        assertThat(updatesTaskStatus.getName()).isEqualTo(STATUS_LABEL);
    }

    @Test
    public void testDelete() throws Exception {
        var createdTaskStatus =createStatus(STATUS_LABEL);
        performByUser(
            delete(TaskStatusController.CONTROLLER_PATH + "/" + createdTaskStatus.id()),
            DEFAULT_EMAIL
        ).andExpect(status().isOk());
        assertThat(taskStatusRepository.count()).isEqualTo(0);
    }

    @SneakyThrows
    private TaskStatusDto createStatus(String statusLabel) {
        var response = performByUser(
            post(TaskStatusController.CONTROLLER_PATH)
                .content(asJson(new TaskStatusDto(null, statusLabel, null)))
                .contentType(MediaType.APPLICATION_JSON),
            DEFAULT_EMAIL
        ).andExpect(status().isCreated())
            .andReturn().getResponse();
        return fromJson(response.getContentAsString(), new TypeReference<TaskStatusDto>() {});
    }
}
