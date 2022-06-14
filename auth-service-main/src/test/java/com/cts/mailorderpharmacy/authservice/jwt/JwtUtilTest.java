package com.cts.mailorderpharmacy.authservice.jwt;

import com.cts.mailorderpharmacy.authservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilTest {
    @Mock
    UserDetails userDetails;

    @Mock
    UserRepository userRepository;

    @Mock
    CustomerDetailsService customerDetailsService;

    @InjectMocks
    JwtUtil jwtUtil;

    @Test
    void generateTokenTest() {
        userDetails = new User("vedant", "vedant", new ArrayList<>());
        String generatedToken = jwtUtil.generateToken(userDetails);
        assertNotNull(generatedToken);
    }

    @Test
    void validateTokenTest() {
        userDetails = new User("vedant", "vedant", new ArrayList<>());
        String generatedToken = jwtUtil.generateToken(userDetails);
        assertTrue(jwtUtil.validateToken(generatedToken));
    }

    @Test
    void extractUsernameFromTokenTest() {
        userDetails = new User("vedant", "vedant", new ArrayList<>());
        String generatedToken = jwtUtil.generateToken(userDetails);
        String username = jwtUtil.extractUsername(generatedToken);
        assertEquals(username, "vedant");
    }
}
