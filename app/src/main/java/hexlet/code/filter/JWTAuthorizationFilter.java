package hexlet.code.filter;

import hexlet.code.component.JWTHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

import static hexlet.code.config.SecurityConfig.DEFAULT_AUTHORITIES;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer";

    private final JWTHelper jwtHelper;
    private final RequestMatcher publicUrls;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return publicUrls.matches(request);
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) {
        var authToken = Optional.ofNullable(request.getHeader(AUTHORIZATION))
            .map(header -> header.replaceFirst("^" + BEARER, ""))
            .map(String::trim)
            .map(jwtHelper::verify)
            .map(claims -> claims.get(SPRING_SECURITY_FORM_USERNAME_KEY))
            .map(Object::toString)
            .map(this::buildAuthToken)
            .orElseThrow();

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken buildAuthToken(final String username) {
        return new UsernamePasswordAuthenticationToken(
            username,
            null,
            DEFAULT_AUTHORITIES
        );
    }
}
