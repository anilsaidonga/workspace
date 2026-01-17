package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.JobCard;
import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.entity.Vehicle;
import com.automotiveserviceplatform.enums.*;
import com.automotiveserviceplatform.payload.request.HoldVehicleRequest;
import com.automotiveserviceplatform.payload.request.LoginRequest;
import com.automotiveserviceplatform.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EdgeCaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private JobCardRepository jobCardRepository;
    
    @Autowired
    private EstimateRepository estimateRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminToken;
    private User customer;

    @BeforeEach
    public void setup() throws Exception {
        // Clean up
        estimateRepository.deleteAll();
        jobCardRepository.deleteAll();
        appointmentRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();

        // Create Admin
        User admin = new User();
        admin.setPhoneNumber("9999999999");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("adminpassword"));
        admin.setRole(UserRole.ADMIN);
        admin.setStatus(AccountStatus.VERIFIED);
        userRepository.save(admin);
        
        // Create Customer
        customer = new User();
        customer.setPhoneNumber("9876543210");
        customer.setEmail("customer@example.com");
        customer.setPassword(passwordEncoder.encode("password123"));
        customer.setRole(UserRole.CUSTOMER);
        customer.setStatus(AccountStatus.VERIFIED);
        userRepository.save(customer);

        // Login Admin
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("9999999999");
        loginRequest.setPassword("adminpassword");

        MvcResult result = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        adminToken = objectMapper.readTree(result.getResponse().getContentAsString()).get("accessToken").asText();
    }

    @Test
    public void testHoldVehicleFlow() throws Exception {
        // 1. Create Hold Vehicle
        HoldVehicleRequest request = new HoldVehicleRequest();
        request.setVehicleNumber("TOW123");
        request.setType(VehicleType.CAR);
        request.setFuelType(FuelType.DIESEL);

        MvcResult result = mockMvc.perform(post("/api/edge-cases/hold-vehicle")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        
        String response = result.getResponse().getContentAsString();
        // Extract Job Card ID from message (simple parsing for test)
        String msg = objectMapper.readTree(response).get("message").asText();
        Long jobCardId = Long.parseLong(msg.split(": ")[1]);

        // 2. Link Customer
        mockMvc.perform(post("/api/edge-cases/link-customer/" + jobCardId)
                .header("Authorization", "Bearer " + adminToken)
                .param("customerId", customer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Customer linked successfully. Job is now PENDING."));
    }
    
    @Test
    public void testAbandonJob() throws Exception {
        // Create a job first (using hold flow for simplicity)
        HoldVehicleRequest request = new HoldVehicleRequest();
        request.setVehicleNumber("ABANDON1");
        request.setType(VehicleType.BIKE);
        request.setFuelType(FuelType.PETROL);

        MvcResult result = mockMvc.perform(post("/api/edge-cases/hold-vehicle")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        
        String msg = objectMapper.readTree(result.getResponse().getContentAsString()).get("message").asText();
        Long jobCardId = Long.parseLong(msg.split(": ")[1]);

        // Mark Abandoned
        mockMvc.perform(post("/api/edge-cases/abandon/" + jobCardId)
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Job marked as ABANDONED."));
    }
}
