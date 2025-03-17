package org.project.pals.config.filters.entrypoints;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        Exception exception = (Exception) request.getAttribute("jwt_exception");

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        if (exception instanceof ExpiredJwtException) {
            response.getWriter().write("{\"error\":\"token expired\"}, \"message\": \"" + exception.getMessage() + "\"}");
        } else if (exception instanceof MalformedJwtException) {
            response.getWriter().write("{ \"error\": \"JWT token unavailable\", \"message\": \"" + exception.getMessage() + "\" }");
        } else {
            response.getWriter().write("{ \"error\": \"Unauthorized request\", \"message\": \"" + authException.getMessage() + "\" }");
        }
    }
}
