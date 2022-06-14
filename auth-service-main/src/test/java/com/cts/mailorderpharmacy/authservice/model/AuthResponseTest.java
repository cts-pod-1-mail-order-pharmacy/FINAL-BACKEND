package com.cts.mailorderpharmacy.authservice.model;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthResponseTest {
    AuthResponse auth = new AuthResponse();

    @Test
    void testUid() {
        auth.setUid("vedant-uid");
        assertEquals("vedant-uid", auth.getUid());
    }

    @Test
    void testName() {
        auth.setName("vedant-name");
        assertEquals("vedant-name", auth.getName());
    }

    @Test
    void testIsValidTrue() {
        auth.setValid(true);
        assertTrue(auth.isValid());
    }

    @Test
    void testValidFalse() {
        auth.setValid(false);
        assertFalse(auth.isValid());
    }
}
