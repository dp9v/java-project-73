package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.controllers.UserController;
import hexlet.code.dtos.UserDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static hexlet.code.Common.DEFAULT_EMAIL;
import static hexlet.code.Common.TEST_EMAIL;
import static hexlet.code.Common.TEST_FIRST_NAME;
import static hexlet.code.Common.TEST_LAST_NAME;
import static hexlet.code.Common.TEST_PASSWORD;
import static hexlet.code.Common.TEST_USER;
import static hexlet.code.Common.asJson;
import static hexlet.code.Common.fromJson;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIT extends BaseIT {

    @Test
    public void testUserRegistration() {
        registerUser(TEST_USER);
        var user = userRepository.findAll().get(1);
        assertThat(user.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(user.getFirstName()).isEqualTo(TEST_FIRST_NAME);
        assertThat(user.getLastName()).isEqualTo(TEST_LAST_NAME);
        assertThat(passwordEncoder.matches(TEST_PASSWORD, user.getPassword())).isTrue();
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

    @SneakyThrows
    @Test
    public void testGetAll() {
        registerUser(TEST_USER);
        var response = perform(get(UserController.CONTROLLER_PATH))
            .andExpect(status().isOk())
            .andReturn().getResponse();
        List<UserDto> users = fromJson(response.getContentAsString(), new TypeReference<>() {
        });
        assertThat(users).hasSize(2);
    }


    @SneakyThrows
    @Test
    public void testUpdate() {
        var user = userRepository.findAll().get(0);
        performByUser(
            put(UserController.CONTROLLER_PATH + "/" + user.getId())
                .content(asJson(TEST_USER))
                .contentType(MediaType.APPLICATION_JSON),
            user.getEmail()
        ).andExpect(status().isOk());
        user = userRepository.findById(user.getId()).orElseThrow();
        assertThat(user.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(user.getFirstName()).isEqualTo(TEST_FIRST_NAME);
        assertThat(user.getLastName()).isEqualTo(TEST_LAST_NAME);
        assertThat(passwordEncoder.matches(TEST_PASSWORD, user.getPassword())).isTrue();
    }

    @Test
    public void testDelete() {
        var createdUser = registerUser(TEST_USER);
        assertThat(userRepository.count()).isEqualTo(2);
        performByUser(
            delete(UserController.CONTROLLER_PATH + "/" + createdUser.id()),
            DEFAULT_EMAIL
        );
        assertThat(userRepository.count()).isEqualTo(1);
    }
}
