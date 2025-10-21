package com.service.productCatalog.filter;

import com.service.productCatalog.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final HttpServletResponse httpServletResponse;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, HttpServletResponse httpServletResponse) {
        this.jwtUtil = jwtUtil;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                var claims = jwtUtil.validateToken(token);
                String userName = claims.getBody().getSubject();
                String role = claims.getBody().get("role", String.class);
                var auth = new UsernamePasswordAuthenticationToken(
                        new User(userName, "",
                                java.util.List.of(() -> "ROLE_" + role)),
                        null,
                        java.util.List.of(() -> "ROLE_" + role)
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                response.setStatus(httpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}
