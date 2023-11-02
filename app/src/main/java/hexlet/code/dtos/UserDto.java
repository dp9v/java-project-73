package hexlet.code.dtos;

import hexlet.code.models.User;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record UserDto(
    Long id,
    @NotBlank @Email String email,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank @Size(min = 3, max = 100) String password,
    @Hidden Date createdAt
) {
    public UserDto(User user){
        this(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), null, user.getCreatedAt());
    }
}
