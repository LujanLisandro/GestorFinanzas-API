package com.lisandro.gestorfinanzas.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class JwtUtilsTest {
    private JwtUtils jwtUtils;

    @BeforeEach
    void setup(){
        jwtUtils = new JwtUtils();
        
    }
    @Test
    void testExtractUsername(){

    }
}
