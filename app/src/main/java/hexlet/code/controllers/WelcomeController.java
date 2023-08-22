package hexlet.code.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class WelcomeController {

    @GetMapping("/welcome")
    public String hello() {
        return "Welcome to Spring";
    }
}
