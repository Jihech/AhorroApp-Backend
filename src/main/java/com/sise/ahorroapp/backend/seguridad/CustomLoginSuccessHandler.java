package com.sise.ahorroapp.backend.seguridad;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.Collection;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectURL = request.getContextPath();

        // Obtener roles con prefijo ROLE_
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

        if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            redirectURL += "/admin_dashboard";
        } else {
            redirectURL += "/usuario_dashboard";
        }


        response.sendRedirect(redirectURL);
    }
}

