package hexlet.code.controllers;


import hexlet.code.dtos.TaskStatusDto;
import hexlet.code.services.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("${base-url}" + TaskStatusController.CONTROLLER_PATH)
public class TaskStatusController {
    public final static String CONTROLLER_PATH = "/statuses";

    private final TaskStatusService service;

    @GetMapping
    public List<TaskStatusDto> getAll() {
        return service.getAll();
    }
}
