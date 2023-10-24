package hexlet.code;

import hexlet.code.controllers.UserController;
import hexlet.code.dtos.UserDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static hexlet.code.Common.TEST_USER;
import static hexlet.code.Common.TEST_USER_NO_PASSWORD;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIT extends BaseIT {

    @Test
    public void testUserRegistration() {
        assertThat(userRepository.count()).isEqualTo(1);
        var createdUser = registerUser(TEST_USER);
        assertThat(userRepository.count()).isEqualTo(2);
        assertThat(createdUser).isEqualTo(TEST_USER_NO_PASSWORD);
    }

    @Test
    @SneakyThrows
    public void testGetUser() {
        var user = userRepository.findAll().get(0);
        performByUser(
            get(UserController.CONTROLLER_PATH + "/" + user.getId()),
            user.getEmail()
        ).andExpect(status().isOk());
    }
}
