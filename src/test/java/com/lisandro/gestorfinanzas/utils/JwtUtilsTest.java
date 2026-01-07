package com.lisandro.gestorfinanzas.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtilsTest {
    private JwtUtils jwtUtils;

    @BeforeEach
    void setup(){
        jwtUtils = new JwtUtils();

        
    }
    @Test
    void testExtractUsername(){
        DecodedJWT mockDecodedJWT = Mockito.mock(DecodedJWT.class);
        Mockito.when(mockDecodedJWT.getSubject()).thenReturn("Lisandro");
        String extractuser = jwtUtils.extractUsername(mockDecodedJWT);
        assertEquals("Lisandro", extractuser, "El resultado deberia ser Lisandro");
    }
    @Test
    void testGetSpecificClaim(){
        DecodedJWT mockDecodedJWT = Mockito.mock(DecodedJWT.class);
        Claim mockClaim = Mockito.mock(Claim.class);
        Mockito.when(mockDecodedJWT.getClaim("authorities")).thenReturn(mockClaim);

        Claim result = jwtUtils.getSpecificClaim(mockDecodedJWT, "authorities");
        assertEquals(mockClaim, result, "El resultado esperado es un Claim");
    }
}
