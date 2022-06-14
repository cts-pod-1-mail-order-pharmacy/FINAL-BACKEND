package com.cts.mailorderpharmacy.authservice;

import com.cts.mailorderpharmacy.authservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {
    User user = new User();

    @Test
    void testUname() {
        user.setUname("vedant-uname");
        assertEquals("vedant-uname", user.getUname());
    }

    @Test
    void testUserid() {
        user.setUserid("vedant-userid");
        assertEquals("vedant-userid", user.getUserid());
    }

    @Test
    void testUpassword() {
        user.setUpassword("vedant-password");
        assertEquals("vedant-password", user.getUpassword());
    }

    @Test
    void testAuthToken() {
        user.setAuthToken("vedant-auth-token");
        assertEquals("vedant-auth-token", user.getAuthToken());
    }
}
