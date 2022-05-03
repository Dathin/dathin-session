package io.github.dathin.dathinsession.security;

import io.github.dathin.boot.dathinstarterauthorizer.model.exception.CustomAuthenticationException;
import io.github.dathin.boot.dathinstarterauthorizer.model.exception.UnauthorizedException;
import io.github.dathin.boot.dathinstarterauthorizer.security.DathinSecurityConfig;
import io.github.dathin.boot.dathinstarterauthorizer.security.DathinSecurityFilter;
import io.github.dathin.boot.dathinstarterauthorizer.security.SecurityFilterExceptionHandler;
import io.github.dathin.boot.dathinstarterauthorizer.service.AuthenticationService;
import io.github.dathin.dathinsession.service.JwtService;
import io.github.dathin.dathinsession.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends DathinSecurityFilter {

    private final JwtService jwtService;

    private final SecurityFilterExceptionHandler securityFilterExceptionHandler;

    private final AuthenticationService authenticationService;

    private final UserService userService;

    public SecurityFilter(JwtService jwtService, SecurityFilterExceptionHandler securityFilterExceptionHandler, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.securityFilterExceptionHandler = securityFilterExceptionHandler;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (token != null) {
                setUserFromToken(token);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (UnauthorizedException ex) {
            securityFilterExceptionHandler.commence(httpServletRequest, httpServletResponse,
                    new CustomAuthenticationException(ex.getError()));
        }
    }

    private void setUserFromToken(String token) {
        var user = jwtService.validateToken(token);
        authenticationService.setAuthenticatedUser(user);
    }

}
