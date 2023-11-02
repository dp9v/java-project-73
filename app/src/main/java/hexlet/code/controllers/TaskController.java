package hexlet.code.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${base-url}" + TaskController.CONTROLLER_PATH)
public class TaskController {
    public final static String CONTROLLER_PATH = "/tasks";
}
