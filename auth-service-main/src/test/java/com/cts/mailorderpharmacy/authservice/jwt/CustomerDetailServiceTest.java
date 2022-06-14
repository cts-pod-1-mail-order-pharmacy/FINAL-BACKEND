package com.cts.mailorderpharmacy.authservice.jwt;

import com.cts.mailorderpharmacy.authservice.model.User;
import com.cts.mailorderpharmacy.authservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

@SpringBootTest
public class CustomerDetailServiceTest {
    UserDetails userDetails;

    @InjectMocks
    CustomerDetailsService customerDetailsService;

    @Mock
    UserRepository userRepository;

    @Test
    void testLoadUserByUsername() {
        User user = new User("vedant", "vedant", "Vedant", null);
        Optional<User> optionalUser = Optional.of(user) ;
        when(userRepository.findById("vedant")).thenReturn(optionalUser);
        userDetails = customerDetailsService.loadUserByUsername("vedant");
        assertEquals(user.getUserid(), userDetails.getUsername());
    }
}
