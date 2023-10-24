package hexlet.code.dtos;

import hexlet.code.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record UserDto(
    @NotBlank @Email String email,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank @Size(min = 3, max = 100) String password
) {
    public UserDto(User user){
        this(user.getEmail(), user.getFirstName(), user.getLastName(), null);
    }
}
