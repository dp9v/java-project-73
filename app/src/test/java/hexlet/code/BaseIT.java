package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.component.JWTHelper;
import hexlet.code.controllers.UserController;
import hexlet.code.dtos.UserDto;
import hexlet.code.repositories.LabelRepository;
import hexlet.code.repositories.TaskRepository;
import hexlet.code.repositories.TaskStatusRepository;
import hexlet.code.repositories.UserRepository;
import hexlet.code.services.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;

import static hexlet.code.Common.DEFAULT_USER;
import static hexlet.code.Common.asJson;
import static hexlet.code.Common.fromJson;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIT {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskStatusRepository taskStatusRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    JWTHelper jwtHelper;
    @Autowired
    PasswordEncoder passwordEncoder;


    @SneakyThrows
    @BeforeEach
    public void setUp() {
        registerDefaultUser();
    }

    @AfterEach
    public void dropAll() {
        userRepository.deleteAll();
        taskRepository.deleteAll();
        taskStatusRepository.deleteAll();
        labelRepository.deleteAll();
    }


    public void registerDefaultUser() {
        registerUser(DEFAULT_USER);
    }

    @SneakyThrows
    public UserDto registerUser(UserDto userDto) {
        var response = mockMvc.perform(
            post(UserController.CONTROLLER_PATH)
                .content(asJson(userDto))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andReturn().getResponse();
        return fromJson(response.getContentAsString(), new TypeReference<>() { });
    }

    @SneakyThrows
    public ResultActions performByUser(MockHttpServletRequestBuilder request, String userEmail) {
        var token = jwtHelper.expiring(Map.of("username", userEmail));
        request.header(AUTHORIZATION, token);
        return perform(request);
    }

    @SneakyThrows
    public ResultActions perform(MockHttpServletRequestBuilder request) {
        return mockMvc.perform(request);
    }
}
