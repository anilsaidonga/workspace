package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.Appointment;
import com.automotiveserviceplatform.entity.JobCard;
import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.entity.Vehicle;
import com.automotiveserviceplatform.enums.*;
import com.automotiveserviceplatform.payload.request.JobCardRequest;
import com.automotiveserviceplatform.payload.request.LoginRequest;
import com.automotiveserviceplatform.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JobCardControllerTest {

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

    private String adminToken;
    private User customer;
    private Vehicle vehicle;

    @BeforeEach
    public void setup() throws Exception {
        // Clean up in correct order
        estimateRepository.deleteAll();
        jobCardRepository.deleteAll();
        appointmentRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
        
        objectMapper.registerModule(new JavaTimeModule());

        // Create Admin User
        User admin = new User();
        admin.setPhoneNumber("9999999999");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("adminpassword"));
        admin.setRole(UserRole.ADMIN);
        admin.setStatus(AccountStatus.VERIFIED);
        userRepository.save(admin);

        // Create Customer User
        customer = new User();
        customer.setPhoneNumber("9876543210");
        customer.setEmail("customer@example.com");
        customer.setPassword(passwordEncoder.encode("password123"));
        customer.setRole(UserRole.CUSTOMER);
        customer.setStatus(AccountStatus.VERIFIED);
        userRepository.save(customer);

        // Create Vehicle
        vehicle = new Vehicle();
        vehicle.setVehicleNumber("AP01AB1234");
        vehicle.setType(VehicleType.CAR);
        vehicle.setFuelType(FuelType.PETROL);
        vehicle.setVehicleCondition(VehicleCondition.DRIVABLE);
        vehicle.setUser(customer);
        vehicleRepository.save(vehicle);

        // Login as Admin to get Token
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("9999999999");
        loginRequest.setPassword("adminpassword");

        MvcResult result = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        adminToken = objectMapper.readTree(response).get("accessToken").asText();
    }

    @Test
    public void testCreateWalkInJobCard() throws Exception {
        JobCardRequest request = new JobCardRequest();
        request.setUserId(customer.getId());
        request.setVehicleId(vehicle.getId());
        request.setJobType(JobType.WORKSHOP);

        mockMvc.perform(post("/api/job-cards/walk-in")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Job Card created successfully!"));
    }

    @Test
    public void testCheckInAppointment() throws Exception {
        // Create Appointment
        Appointment appointment = new Appointment();
        appointment.setUser(customer);
        appointment.setVehicle(vehicle);
        appointment.setAppointmentType(AppointmentType.SELF_DRIVE);
        appointment.setTimeSlot(LocalDateTime.now().plusDays(1));
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);

        mockMvc.perform(post("/api/job-cards/check-in/" + appointment.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Appointment checked-in and Job Card created!"));
    }

    @Test
    public void testUpdateJobStatus() throws Exception {
        // Create Job Card
        JobCard jobCard = new JobCard();
        jobCard.setUser(customer);
        jobCard.setVehicle(vehicle);
        jobCard.setJobType(JobType.WORKSHOP);
        jobCard.setJobStatus(JobStatus.PENDING);
        jobCardRepository.save(jobCard);

        mockMvc.perform(put("/api/job-cards/" + jobCard.getId() + "/status")
                .header("Authorization", "Bearer " + adminToken)
                .param("status", "IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Job status updated successfully!"));
    }
}
