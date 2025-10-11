package com.servifacil.SF_BackEnd.security;

import com.servifacil.SF_BackEnd.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // ignora endpoints públicos
        if (path.equals("/api/users/login") || path.equals("/api/users/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String email = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                try {
                    email = jwtUtil.extractEmail(token);
                } catch (Exception e) {
                    System.out.println("Erro ao extrair email do token: " + e.getMessage());
                }

                try {
                    int userId = jwtUtil.extractId(token);
                    request.setAttribute("userId", userId); // ✅ agora o id estará disponível na Controller
                } catch (Exception e) {
                    System.out.println("Erro ao extrair id do token: " + e.getMessage());
                }
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validateToken(token)) {
                    var user = userRepository.findByEmail(email).orElse(null);

                    if (user != null) {
                        // Configura roles do usuário
                        List<SimpleGrantedAuthority> authorities = List.of(
                                new SimpleGrantedAuthority("ROLE_" + user.getUserType().name())
                        );

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(user, null, authorities);

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                    else {
                        // Token inválido → 401 Unauthorized
                        response.setContentType("application/json");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("{\"error\": \"Token inválido ou expirado\"}");
                        return;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Erro no JwtFilter: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}