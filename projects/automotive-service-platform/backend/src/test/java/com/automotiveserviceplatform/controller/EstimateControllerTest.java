package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.*;
import com.automotiveserviceplatform.enums.*;
import com.automotiveserviceplatform.payload.request.EstimateRequest;
import com.automotiveserviceplatform.payload.request.LabourItemRequest;
import com.automotiveserviceplatform.payload.request.LoginRequest;
import com.automotiveserviceplatform.payload.request.PartItemRequest;
import com.automotiveserviceplatform.repository.*;
import com.automotiveserviceplatform.security.jwt.JwtUtils;
import com.automotiveserviceplatform.security.services.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EstimateControllerTest {

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
    private PartRepository partRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private JwtUtils jwtUtils;

    private String adminToken;
    private String customerToken;
    private JobCard jobCard;
    private Part part;

    @BeforeEach
    public void setup() throws Exception {
        // Clean up in correct order
        estimateRepository.deleteAll();
        jobCardRepository.deleteAll();
        appointmentRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
        partRepository.deleteAll();

        // Create Admin
        User admin = new User();
        admin.setPhoneNumber("9999999999");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("adminpassword"));
        admin.setRole(UserRole.ADMIN);
        admin.setStatus(AccountStatus.VERIFIED);
        userRepository.save(admin);

        // Create Customer
        User customer = new User();
        customer.setPhoneNumber("9876543210");
        customer.setEmail("customer@example.com");
        customer.setPassword(passwordEncoder.encode("password123"));
        customer.setRole(UserRole.CUSTOMER);
        customer.setStatus(AccountStatus.VERIFIED);
        userRepository.save(customer);

        // Create Vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("AP01AB1234");
        vehicle.setType(VehicleType.CAR);
        vehicle.setFuelType(FuelType.PETROL);
        vehicle.setVehicleCondition(VehicleCondition.DRIVABLE);
        vehicle.setUser(customer);
        vehicleRepository.save(vehicle);

        // Create Job Card
        jobCard = new JobCard();
        jobCard.setUser(customer);
        jobCard.setVehicle(vehicle);
        jobCard.setJobType(JobType.WORKSHOP);
        jobCard.setJobStatus(JobStatus.PENDING);
        jobCardRepository.save(jobCard);
        
        // Create Part
        part = new Part();
        part.setName("Oil Filter");
        part.setQuantity(10);
        partRepository.save(part);

        // Login Admin (Password Flow)
        LoginRequest adminLogin = new LoginRequest();
        adminLogin.setUsername("9999999999");
        adminLogin.setPassword("adminpassword");
        MvcResult adminResult = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminLogin)))
                .andReturn();
        adminToken = objectMapper.readTree(adminResult.getResponse().getContentAsString()).get("accessToken").asText();

        // Login Customer (Manual Token Generation)
        UserDetailsImpl userDetails = UserDetailsImpl.build(customer);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        customerToken = jwtUtils.generateJwtToken(authentication);
    }

    @Test
    public void testCreateEstimate() throws Exception {
        EstimateRequest request = new EstimateRequest();
        request.setJobCardId(jobCard.getId());
        request.setTaxes(new BigDecimal("50.00"));
        
        LabourItemRequest labour = new LabourItemRequest();
        labour.setDescription("General Service");
        labour.setCost(new BigDecimal("500.00"));
        request.setLabourItems(Collections.singletonList(labour));
        
        PartItemRequest partItem = new PartItemRequest();
        partItem.setPartId(part.getId());
        partItem.setQuantity(1);
        partItem.setCost(new BigDecimal("200.00"));
        request.setPartItems(Collections.singletonList(partItem));

        mockMvc.perform(post("/api/estimates")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Estimate created successfully!"));
    }

    @Test
    public void testApproveEstimate() throws Exception {
        // Create Estimate first
        Estimate estimate = new Estimate();
        estimate.setJobCard(jobCard);
        estimate.setTotalAmount(new BigDecimal("750.00"));
        estimate.setApproved(false);
        estimateRepository.save(estimate);

        mockMvc.perform(post("/api/estimates/" + estimate.getId() + "/approve")
                .header("Authorization", "Bearer " + customerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Estimate approved successfully!"));
    }
}
