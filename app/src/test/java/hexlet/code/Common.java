package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dtos.UserDto;
import lombok.SneakyThrows;

public class Common {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final String DEFAULT_EMAIL = "email@email.com";
    public static final String DEFAULT_FIRST_NAME = "first_name";
    public static final String DEFAULT_LAST_NAME = "last_name";
    public static final String DEFAULT_PASSWORD = "pass";
    public static final String TEST_EMAIL = "email2@email.com";
    public static final String TEST_FIRST_NAME = "first_name";
    public static final String TEST_LAST_NAME = "last_name";
    public static final String TEST_PASSWORD = "pass_test";

    public static final UserDto DEFAULT_USER = new UserDto(
        null, DEFAULT_EMAIL, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_PASSWORD
    );

    public static final UserDto TEST_USER = new UserDto(
        null, TEST_EMAIL, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_PASSWORD
    );

    @SneakyThrows
    public static String asJson(final Object object) {
        return MAPPER.writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> T fromJson(final String json, final TypeReference<T> to) {
        return MAPPER.readValue(json, to);
    }


}
