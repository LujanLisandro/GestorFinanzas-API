package com.lisandro.gestorfinanzas.Security.Filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.lisandro.gestorfinanzas.service.auth.TokenBlacklistService;
import com.lisandro.gestorfinanzas.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;
    private TokenBlacklistService tokenBlacklistService;

    public JwtTokenValidator(JwtUtils jwtUtils, TokenBlacklistService tokenBlacklistService) {
        this.jwtUtils = jwtUtils;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            // Recibo el token a traves del header y le saco la palabra bearer
            jwtToken = jwtToken.substring(7);

            // Verificar si el token está en la blacklist
            if (tokenBlacklistService.isTokenBlacklisted(jwtToken)) {
                System.out.println("⛔ Token en blacklist - logout realizado");
                filterChain.doFilter(request, response);
                return; // No autenticar
            }

            try {
                // Se decodifica el token
                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
                // Traemos el nombre de usuario del claim
                String username = jwtUtils.extractUsername(decodedJWT);
                // Traemos los permisos del claim
                String authoritiesString = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

                // Manejar authorities vacías o null
                Collection<? extends GrantedAuthority> authoritiesList;
                if (authoritiesString != null && !authoritiesString.isEmpty()) {
                    authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesString);
                } else {
                    authoritiesList = AuthorityUtils.NO_AUTHORITIES;
                }

                SecurityContext context = SecurityContextHolder.getContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                        authoritiesList);
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

                System.out.println("✅ Usuario autenticado: " + username);
                System.out.println("✅ Authorities: " + authoritiesList);
            } catch (Exception e) {
                // Si hay error al validar, simplemente no autenticamos
                // El filtro de seguridad manejará la respuesta 401/403
                System.out.println("❌ ERROR AL VALIDAR TOKEN: " + e.getMessage());
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }

}