package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.enums.AccountStatus;
import com.automotiveserviceplatform.enums.UserRole;
import com.automotiveserviceplatform.payload.request.LoginRequest;
import com.automotiveserviceplatform.payload.request.OtpVerificationRequest;
import com.automotiveserviceplatform.payload.request.PhoneNumberRequest;
import com.automotiveserviceplatform.repository.*;
import com.automotiveserviceplatform.service.OtpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

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
    
    @Autowired
    private OtpService otpService;

    @BeforeEach
    public void setup() {
        estimateRepository.deleteAll();
        jobCardRepository.deleteAll();
        appointmentRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testStaffLogin() throws Exception {
        // Create SUPER_ADMIN
        User admin = new User();
        admin.setPhoneNumber("0000000000");
        admin.setName("Super Admin");
        admin.setPassword(passwordEncoder.encode("supersecret"));
        admin.setRole(UserRole.SUPER_ADMIN);
        admin.setStatus(AccountStatus.VERIFIED);
        userRepository.save(admin);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("0000000000");
        loginRequest.setPassword("supersecret");

        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    public void testCustomerOtpFlow() throws Exception {
        PhoneNumberRequest request = new PhoneNumberRequest();
        request.setPhoneNumber("9876543210");

        mockMvc.perform(post("/api/auth/send-otp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OTP sent successfully!"));
        
        // Verify failure with wrong OTP
        OtpVerificationRequest verifyRequest = new OtpVerificationRequest();
        verifyRequest.setPhoneNumber("9876543210");
        verifyRequest.setOtp("000000");

        mockMvc.perform(post("/api/auth/verify-otp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(verifyRequest)))
                .andExpect(status().isBadRequest());
    }
}
