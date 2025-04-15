package com.vhkhai.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.error("Access denied error: {}", accessDeniedException.getMessage());
        log.info("Request URL: {}", request.getRequestURL());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Map<String, Object> errorResponse = Map.of(
                "status", HttpServletResponse.SC_FORBIDDEN,
                "message", "Access is denied",
                "timestamp", new Date().toString()
        );
        String json = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(json);
    }
}

