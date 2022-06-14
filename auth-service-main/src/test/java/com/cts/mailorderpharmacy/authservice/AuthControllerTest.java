package com.cts.mailorderpharmacy.authservice;

import com.cts.mailorderpharmacy.authservice.controller.AuthController;
import com.cts.mailorderpharmacy.authservice.jwt.CustomerDetailServiceTest;
import com.cts.mailorderpharmacy.authservice.jwt.CustomerDetailsService;
import com.cts.mailorderpharmacy.authservice.jwt.JwtUtil;
import com.cts.mailorderpharmacy.authservice.model.AuthResponse;
import com.cts.mailorderpharmacy.authservice.model.User;
import com.cts.mailorderpharmacy.authservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    AuthResponse authResponse;

    UserDetails userDetails;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @Mock
    CustomerDetailsService customerDetailService;

    @Mock
    UserRepository userRepository;

    @Test
    void testLogin() {
        User user = new User("vedant", "vedant", null, null);
        org.springframework.security.core.userdetails.User value = new org.springframework.security.core.userdetails.User(user.getUserid(), user.getUpassword(), new ArrayList<>());
        when(customerDetailService.loadUserByUsername("vedant")).thenReturn(value);
        UserDetails userDetails1 = customerDetailService.loadUserByUsername("vedant");
        when(jwtUtil.generateToken(userDetails1)).thenReturn("token");
        ResponseEntity<?> login = authController.login(user);
        assertEquals( 200, login);
    }

    @Test
    void loginTestFailed() {
        User user = new User("vedant", "vedant",null,null);
        userDetails = customerDetailService.loadUserByUsername("vedant");
        UserDetails value = new org.springframework.security.core.userdetails.User(user.getUserid(), "vedantNotCorrectPassword", new ArrayList<>());
        when(customerDetailService.loadUserByUsername("vedant")).thenReturn(value);
        when(jwtUtil.generateToken(userDetails)).thenReturn("token");
        ResponseEntity<?> login = authController.login(user);
        assertEquals( 403, login.getStatusCodeValue());
    }

    @Test
    void validateTestValidtoken() {
        when(jwtUtil.validateToken("token")).thenReturn(true);
        when(jwtUtil.extractUsername("token")).thenReturn("vedant");
        User user1 = new User("vedant", "vedant", "vedant",null);
        Optional<User> data = Optional.of(user1);
        when(userRepository.findById("vedant")).thenReturn(data);
        ResponseEntity<?> validity = authController.getValidity("bearer token");
        assertTrue(validity.getBody().toString().contains("true"));
    }

    @Test
    void validateTestInValidtoken() {
        when(jwtUtil.validateToken("token")).thenReturn(false);
        ResponseEntity<?> validity = authController.getValidity("bearer token");
        assertTrue(validity.getBody().toString().contains("false"));
    }
}
