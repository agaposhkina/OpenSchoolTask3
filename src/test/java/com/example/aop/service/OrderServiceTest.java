package com.example.aop.service;

import com.example.aop.exception.ResourceNotFoundException;
import com.example.aop.model.Order;
import com.example.aop.repository.OrderRepository;
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
public class OrderServiceTest {
    @MockBean
    private OrderRepository mockOrderRepository;
    @Autowired
    private OrderService orderService;

    @Test
    void logCreationTest(CapturedOutput output) {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setDescription("Description test");
        Mockito.when(mockOrderRepository.save(mockOrder)).thenReturn(mockOrder);

        orderService.createOrder(mockOrder);

        assertTrue(output.getOut().contains("Executing OrderService.createOrder with params: [" + mockOrder + "]"));
        assertTrue(output.getOut().contains("OrderService.createOrder executed successfully. Returns: " + mockOrder));
    }

    @Test
    void logGetTest(CapturedOutput output) {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setDescription("Description test");
        Mockito.when(mockOrderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));

        orderService.getOrderById(1L);

        assertTrue(output.getOut().contains("Executing OrderService.getOrderById with params: [" + 1L + "]"));
        assertTrue(output.getOut().contains("OrderService.getOrderById executed successfully. Returns: " + mockOrder));
    }

    @Test
    void logGetNonExistentTest(CapturedOutput output) {
        String errorMsg = "No order found for id = " + 1L;
        Mockito.when(mockOrderRepository.findById(1L))
                .thenThrow(new ResourceNotFoundException(errorMsg));

        assertThrows(ResourceNotFoundException.class, ()-> orderService.getOrderById(1L));
        assertTrue(output.getOut().contains("Executing OrderService.getOrderById with params: [" + 1L + "]"));
        assertTrue(output.getOut().contains("Exception while executing OrderService.getOrderById: " + errorMsg));

    }
}
