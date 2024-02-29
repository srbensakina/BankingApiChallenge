package com.chanllenge.bankingapi.controller;

import com.chanllenge.bankingapi.dto.BalanceResponse;
import com.chanllenge.bankingapi.service.CustomerService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestPropertySource(locations = "classpath:application-test.properties")
class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword")
           .withInitScript("test-data.sql");
   // com/chanllenge/bankingapi/controller
    /* @BeforeEach
     void setUp() {
         MockitoAnnotations.openMocks(this);
     }*/
    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void testGetBalances_Success() {
        // Arrange
        Long customerId = 1L;

        List<BalanceResponse> balances = Arrays.asList(
                new BalanceResponse(1L, 500F),
                new BalanceResponse(2L, 1000F)
        );

        when(customerService.getBalances(customerId)).thenReturn(balances);

        ResponseEntity<?> response = customerController.getBalances(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(balances, response.getBody());
        verify(customerService, times(1)).getBalances(customerId);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void testGetBalances_NotFound() {
        Long customerId = 1L;

        when(customerService.getBalances(customerId)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = customerController.getBalances(customerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(customerService, times(1)).getBalances(customerId);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void getAllCustomers() throws Exception {

            mockMvc.perform(get("/api/customers"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("Arisha Barron"));
        }

    }
