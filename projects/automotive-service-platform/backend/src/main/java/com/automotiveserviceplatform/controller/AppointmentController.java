package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.Appointment;
import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.entity.Vehicle;
import com.automotiveserviceplatform.enums.AppointmentStatus;
import com.automotiveserviceplatform.payload.request.AppointmentRequest;
import com.automotiveserviceplatform.payload.response.MessageResponse;
import com.automotiveserviceplatform.repository.AppointmentRepository;
import com.automotiveserviceplatform.repository.UserRepository;
import com.automotiveserviceplatform.repository.VehicleRepository;
import com.automotiveserviceplatform.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        Vehicle vehicle = vehicleRepository.findById(appointmentRequest.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Error: Vehicle not found."));

        if (!vehicle.getUser().getId().equals(user.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: You can only book appointments for your own vehicles."));
        }

        // Basic slot availability check (simplified for now)
        // In a real app, we'd check if the slot is already taken or if capacity is full
        if (appointmentRequest.getTimeSlot().isBefore(LocalDateTime.now())) {
             return ResponseEntity.badRequest().body(new MessageResponse("Error: Cannot book appointment in the past."));
        }

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setVehicle(vehicle);
        appointment.setAppointmentType(appointmentRequest.getAppointmentType());
        appointment.setTimeSlot(appointmentRequest.getTimeSlot());
        appointment.setStatus(AppointmentStatus.CONFIRMED); // Auto-confirm for now

        appointmentRepository.save(appointment);

        return ResponseEntity.ok(new MessageResponse("Appointment booked successfully!"));
    }

    @GetMapping("/my-appointments")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Appointment>> getMyAppointments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<Appointment> appointments = appointmentRepository.findByUserId(userDetails.getId());
        return ResponseEntity.ok(appointments);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return ResponseEntity.ok(appointments);
    }
    
    // Placeholder for slot availability logic
    @GetMapping("/slots")
    public ResponseEntity<?> getAvailableSlots() {
        // This would return a list of available time slots based on business logic
        return ResponseEntity.ok(new MessageResponse("Slots logic to be implemented."));
    }
}
