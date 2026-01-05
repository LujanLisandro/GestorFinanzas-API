package com.lisandro.gestorfinanzas.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.lisandro.gestorfinanzas.Security.Filter.JwtTokenValidator;
import com.lisandro.gestorfinanzas.filter.RateLimitFilter;
import com.lisandro.gestorfinanzas.service.auth.TokenBlacklistService;
import com.lisandro.gestorfinanzas.utils.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private RateLimitFilter rateLimitFilter;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita una proteccion INVESTIGAR
                .cors(cors -> cors.configurationSource(corsConfigurationSource)) // Habilita CORS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Politica
                .logout(logout -> logout.disable()) // Deshabilita logout automático (no se usa con JWT)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Público
                        .requestMatchers("/api/users").permitAll() // Público
                        .requestMatchers("/api/dolar").permitAll() // Público
                        .requestMatchers("/actuator/health").permitAll()
                        .anyRequest().authenticated() // Todo lo demás protegido
                )
                .addFilterBefore(rateLimitFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidator(jwtUtils, tokenBlacklistService),
                        BasicAuthenticationFilter.class);
        return http.build(); // "CREAR" LA HTTP Y BUILDERLA
    }

    // AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // AUTH PROVIDERS
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        // Crea una instancia de authProvider para utilizarlo como objeto
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());//
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    // Algoritmo de encriptacion
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}