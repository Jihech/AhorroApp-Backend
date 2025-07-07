package com.sise.ahorroapp.backend.seguridad;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Obtener roles del usuario autenticado
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Redirigir según el rol
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/dashboard");  // 👈 Vista específica para admin
        } else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/usuario/dashboard"); // 👈 Vista específica para usuario
        } else {
            response.sendRedirect("/?error=true"); // 👈 Seguridad extra por si no hay rol
        }
    }
}
