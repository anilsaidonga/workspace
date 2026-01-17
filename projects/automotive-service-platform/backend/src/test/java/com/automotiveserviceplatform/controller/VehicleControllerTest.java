package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.entity.Vehicle;
import com.automotiveserviceplatform.enums.*;
import com.automotiveserviceplatform.payload.request.LoginRequest;
import com.automotiveserviceplatform.payload.request.VehicleRequest;
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
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private JobCardRepository jobCardRepository;
    
    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private User user;

    @BeforeEach
    public void setup() throws Exception {
        // Clean up in correct order
        estimateRepository.deleteAll();
        jobCardRepository.deleteAll();
        appointmentRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();

        // Create User
        user = new User();
        user.setPhoneNumber("9876543210");
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(UserRole.CUSTOMER);
        user.setStatus(AccountStatus.VERIFIED);
        userRepository.save(user);

        // Login to get Token
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("9876543210");
        loginRequest.setPassword("password123");

        MvcResult result = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        token = objectMapper.readTree(response).get("accessToken").asText();
    }

    @Test
    public void testAddVehicle() throws Exception {
        VehicleRequest vehicleRequest = new VehicleRequest();
        vehicleRequest.setVehicleNumber("AP01AB1234");
        vehicleRequest.setType(VehicleType.CAR);
        vehicleRequest.setFuelType(FuelType.PETROL);
        vehicleRequest.setCondition(VehicleCondition.DRIVABLE);

        mockMvc.perform(post("/api/vehicles")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Vehicle added successfully!"));
    }

    @Test
    public void testGetMyVehicles() throws Exception {
        // Add a vehicle first
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("AP01AB1234");
        vehicle.setType(VehicleType.CAR);
        vehicle.setFuelType(FuelType.PETROL);
        vehicle.setVehicleCondition(VehicleCondition.DRIVABLE);
        vehicle.setUser(user);
        vehicleRepository.save(vehicle);

        mockMvc.perform(get("/api/vehicles")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vehicleNumber").value("AP01AB1234"));
    }
}
