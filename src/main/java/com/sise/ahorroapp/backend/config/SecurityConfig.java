package com.sise.ahorroapp.backend.config;

import com.sise.ahorroapp.backend.seguridad.CustomLoginSuccessHandler;
import com.sise.ahorroapp.backend.servicio.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private CustomLoginSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // ✅ Rutas públicas, incluyendo vendor y webfonts
                .requestMatchers("/", "/css/**", "/js/**", "/img/**", "/vendor/**", "/webfonts/**", "/usuarios/nuevo", "/api/**").permitAll()

                // ✅ Accesibles por usuarios autenticados (USER y ADMIN)
                .requestMatchers("/usuario", "/usuario/**", "/deudas/**", "/metas/**", "/dashboard").hasAnyRole("USER", "ADMIN")

                // ✅ Solo ADMIN
                .requestMatchers("/usuarios/**", "/movimientos/**", "/perfil").hasRole("ADMIN")
                .requestMatchers("/dashboard").hasAnyRole("ADMIN", "USER")


                // ✅ Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/") // Página de login personalizada
                .loginProcessingUrl("/login")
                .usernameParameter("correo")
                .passwordParameter("clave")
                .successHandler(successHandler)
                .failureUrl("/?error=true") // Redirección al fallar
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/error")
            )
            .userDetailsService(usuarioDetailsService)
            .csrf(csrf -> csrf.disable()); // ⚠️ Desactiva CSRF solo si lo tienes controlado

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // ⚠️ Solo para desarrollo
        // return new BCryptPasswordEncoder();   // ✅ Usa esto en producción
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(usuarioDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }

    @Bean
    public HttpFirewall allowSemicolonFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(HttpFirewall firewall) {
        return web -> web.httpFirewall(firewall);
    }
}
