package net.engineeringdigest.journalApp.entity;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

@SpringBootTest
public class UserDetailsServiceimplTests {
    @InjectMocks
    private UserDetailsServiceimpl userDetailsServiceimpl;

    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void loadUserByUsernameTests()
    {
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("vegeta").password("password").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsServiceimpl.loadUserByUsername("vegeta");
        Assertions.assertNotNull(user);
    }
}
