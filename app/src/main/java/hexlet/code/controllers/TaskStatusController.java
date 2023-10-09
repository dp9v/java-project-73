package hexlet.code.controllers;


import hexlet.code.dtos.TaskStatusDto;
import hexlet.code.services.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;


@RequiredArgsConstructor
@RestController
@RequestMapping("${base-url}" + TaskStatusController.CONTROLLER_PATH)
public class TaskStatusController {
    public final static String CONTROLLER_PATH = "/statuses";

    private final TaskStatusService service;

    @GetMapping
    public List<TaskStatusDto> getAll() {
        return service.getAll()
            .stream()
            .map(TaskStatusDto::new)
            .toList();
    }

    @Operation(summary = "Get state by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "State found"),
        @ApiResponse(responseCode = "404", description = "State with that id not found")
    })
    @GetMapping("/{id}")
    public TaskStatusDto getById(@PathVariable long id) {
        return new TaskStatusDto(
            service.getById(id)
        );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TaskStatusDto createNew(@RequestBody @Valid TaskStatusDto dto) {
        return new TaskStatusDto(
            service.create(dto)
        );
    }

    @PutMapping("/{id}")
    public TaskStatusDto updateState(
        @PathVariable long id,
        @RequestBody @Valid TaskStatusDto dto
    ) {
        return new TaskStatusDto(
            service.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }


}
