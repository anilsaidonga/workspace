package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.entity.Vehicle;
import com.automotiveserviceplatform.enums.*;
import com.automotiveserviceplatform.payload.request.AppointmentRequest;
import com.automotiveserviceplatform.repository.*;
import com.automotiveserviceplatform.security.jwt.JwtUtils;
import com.automotiveserviceplatform.security.services.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

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
    private JwtUtils jwtUtils;

    private String token;
    private User user;
    private Vehicle vehicle;

    @BeforeEach
    public void setup() throws Exception {
        // Clean up in correct order
        estimateRepository.deleteAll();
        jobCardRepository.deleteAll();
        appointmentRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
        
        // Register JavaTimeModule for LocalDateTime serialization
        objectMapper.registerModule(new JavaTimeModule());

        // Create User
        user = new User();
        user.setPhoneNumber("9876543210");
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(UserRole.CUSTOMER);
        user.setStatus(AccountStatus.VERIFIED);
        userRepository.save(user);

        // Create Vehicle
        vehicle = new Vehicle();
        vehicle.setVehicleNumber("AP01AB1234");
        vehicle.setType(VehicleType.CAR);
        vehicle.setFuelType(FuelType.PETROL);
        vehicle.setVehicleCondition(VehicleCondition.DRIVABLE);
        vehicle.setUser(user);
        vehicleRepository.save(vehicle);

        // Generate Token manually for Customer
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        token = jwtUtils.generateJwtToken(authentication);
    }

    @Test
    public void testCreateAppointment() throws Exception {
        AppointmentRequest request = new AppointmentRequest();
        request.setVehicleId(vehicle.getId());
        request.setAppointmentType(AppointmentType.SELF_DRIVE);
        request.setTimeSlot(LocalDateTime.now().plusDays(1));

        mockMvc.perform(post("/api/appointments")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Appointment booked successfully!"));
    }

    @Test
    public void testCreateAppointment_NoToken_ShouldReturnUnauthorized() throws Exception {
        AppointmentRequest request = new AppointmentRequest();
        request.setVehicleId(vehicle.getId());
        request.setAppointmentType(AppointmentType.SELF_DRIVE);
        request.setTimeSlot(LocalDateTime.now().plusDays(1));

        mockMvc.perform(post("/api/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetMyAppointments() throws Exception {
        // Create an appointment first
        com.automotiveserviceplatform.entity.Appointment appointment = new com.automotiveserviceplatform.entity.Appointment();
        appointment.setUser(user);
        appointment.setVehicle(vehicle);
        appointment.setAppointmentType(AppointmentType.SELF_DRIVE);
        appointment.setTimeSlot(LocalDateTime.now().plusDays(1));
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);

        mockMvc.perform(get("/api/appointments/my-appointments")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CONFIRMED"));
    }
}
