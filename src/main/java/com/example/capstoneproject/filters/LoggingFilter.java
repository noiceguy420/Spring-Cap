package com.example.capstoneproject.filters;

import com.example.capstoneproject.services.LoggerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {
    private final LoggerService logger;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.log("Request: " + request.getRequestURI());
        filterChain.doFilter(request, response);
        logger.log("Response: " + response.getStatus());
    }
}
