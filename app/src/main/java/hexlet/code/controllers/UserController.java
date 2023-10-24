package hexlet.code.controllers;


import hexlet.code.dtos.UserDto;
import hexlet.code.models.User;
import hexlet.code.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("${base-url}" + UserController.CONTROLLER_PATH)
public class UserController {
    public final static String CONTROLLER_PATH = "/users";

    private final UserService userService;

    @ApiResponses(@ApiResponse(responseCode = "200", content =
    @Content(schema = @Schema(implementation = User.class)))
    )
    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll()
            .stream()
            .map(UserDto::new)
            .toList();
    }

    @ApiResponses(@ApiResponse(responseCode = "200"))
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable final Long id) {
        return new UserDto(
            userService.getById(id)
        );
    }

    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201", description = "User created")
    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto registerNew(@RequestBody @Valid final UserDto dto) {
        return new UserDto(
            userService.createNewUser(dto)
        );
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable final long id, @RequestBody @Valid final UserDto dto) {
        return new UserDto(
            userService.updateUser(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final long id) {
        userService.delete(id);
    }
}
