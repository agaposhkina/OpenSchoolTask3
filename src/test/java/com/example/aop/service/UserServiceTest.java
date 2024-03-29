package com.example.aop.service;

import com.example.aop.exception.ResourceNotFoundException;
import com.example.aop.model.User;
import com.example.aop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith({OutputCaptureExtension.class, MockitoExtension.class})
public class UserServiceTest {
    @MockBean
    private UserRepository mockUserRepository;
    @Autowired
    private UserService userService;

    @Test
    void logCreationTest(CapturedOutput output) {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Test user");
        mockUser.setEmail("test@test.com");
        Mockito.when(mockUserRepository.save(mockUser)).thenReturn(mockUser);

        userService.createUser(mockUser);

        assertTrue(output.getOut().contains("Executing UserService.createUser with params: [" + mockUser + "]"));
        assertTrue(output.getOut().contains("UserService.createUser executed successfully. Returns: " + mockUser));
    }

    @Test
    void logGetTest(CapturedOutput output) {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Test user");
        mockUser.setEmail("test@test.com");
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        userService.getUserById(1L);

        assertTrue(output.getOut().contains("Executing UserService.getUserById with params: [" + 1L + "]"));
        assertTrue(output.getOut().contains("UserService.getUserById executed successfully. Returns: " + mockUser));
    }

    @Test
    void logGetNonExistentTest(CapturedOutput output) {
        String errorMsg = "No user found for id = " + 1L;
        Mockito.when(mockUserRepository.findById(1L))
                .thenThrow(new ResourceNotFoundException(errorMsg));

        assertThrows(ResourceNotFoundException.class, ()-> userService.getUserById(1L));
        assertTrue(output.getOut().contains("Executing UserService.getUserById with params: [" + 1L + "]"));
        assertTrue(output.getOut().contains("Exception while executing UserService.getUserById: " + errorMsg));

    }
}
