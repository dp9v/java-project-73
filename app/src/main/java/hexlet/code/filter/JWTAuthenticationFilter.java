package hexlet.code.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.component.JWTHelper;
import hexlet.code.dtos.LoginDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper mapper;
    private final JWTHelper jwtHelper;

    public JWTAuthenticationFilter(
        AuthenticationManager authenticationManager,
        RequestMatcher loginUrls,
        JWTHelper jwtHelper
    ) {
        super(authenticationManager);
        super.setRequiresAuthenticationRequestMatcher(loginUrls);
        this.mapper = new ObjectMapper();
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        var loginDto = getLoginData(request);
        var authenticationToken = new UsernamePasswordAuthenticationToken(
            loginDto.email(),
            loginDto.password()
        );
        setDetails(request, authenticationToken);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @SneakyThrows
    private LoginDto getLoginData(final HttpServletRequest request) {
        final String json = request.getReader()
            .lines()
            .collect(Collectors.joining());
        return mapper.readValue(json, LoginDto.class);
    }

    @Override
    @SneakyThrows
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult
    ) {
        var userDetails = (UserDetails) authResult.getPrincipal();
        final String token = jwtHelper.expiring(
            Map.of(SPRING_SECURITY_FORM_USERNAME_KEY, userDetails.getUsername())
        );
        response.getWriter().println(token);
    }
}
