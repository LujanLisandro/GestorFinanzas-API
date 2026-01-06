package com.lisandro.gestorfinanzas.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.auth0.jwt.interfaces.DecodedJWT;

@ExtendWith(SpringExtension.class)
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
        
    }
}
