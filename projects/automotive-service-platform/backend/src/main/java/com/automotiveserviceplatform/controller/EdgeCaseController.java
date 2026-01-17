package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.JobCard;
import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.entity.Vehicle;
import com.automotiveserviceplatform.enums.*;
import com.automotiveserviceplatform.payload.request.HoldVehicleRequest;
import com.automotiveserviceplatform.payload.response.MessageResponse;
import com.automotiveserviceplatform.repository.JobCardRepository;
import com.automotiveserviceplatform.repository.UserRepository;
import com.automotiveserviceplatform.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/edge-cases")
public class EdgeCaseController {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobCardRepository jobCardRepository;

    // 1. Vehicle on HOLD (Towed without owner)
    @PostMapping("/hold-vehicle")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createHoldVehicle(@Valid @RequestBody HoldVehicleRequest request) {
        if (vehicleRepository.existsByVehicleNumber(request.getVehicleNumber())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Vehicle already exists."));
        }

        // Create a temporary vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setType(request.getType());
        vehicle.setFuelType(request.getFuelType());
        vehicle.setVehicleCondition(VehicleCondition.TOWED_NO_OWNER);
        
        // We need to assign it to a user. 
        // Strategy: Assign to a special "HOLD_USER" or leave null if DB allows (our DB requires user_id).
        // Better: Create/Find a "Shadow" user for this purpose.
        User shadowUser = userRepository.findByPhoneNumber("SHADOW_USER")
                .orElseGet(() -> {
                    User u = new User();
                    u.setPhoneNumber("SHADOW_USER");
                    u.setName("Unclaimed Vehicle Owner");
                    u.setRole(UserRole.CUSTOMER);
                    u.setStatus(AccountStatus.PROVISIONAL);
                    u.setPassword("shadow"); // Dummy
                    u.setEmail("shadow@temp.com");
                    return userRepository.save(u);
                });
        
        vehicle.setUser(shadowUser);
        vehicleRepository.save(vehicle);

        // Create a Job Card in ON_HOLD status
        JobCard jobCard = new JobCard();
        jobCard.setUser(shadowUser);
        jobCard.setVehicle(vehicle);
        jobCard.setJobType(JobType.WORKSHOP);
        jobCard.setJobStatus(JobStatus.ON_HOLD);
        
        jobCardRepository.save(jobCard);

        return ResponseEntity.ok(new MessageResponse("Vehicle placed on HOLD. Job Card ID: " + jobCard.getId()));
    }

    // 2. Link Customer to Hold Vehicle
    @PostMapping("/link-customer/{jobCardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> linkCustomerToJob(@PathVariable Long jobCardId, @RequestParam Long customerId) {
        JobCard jobCard = jobCardRepository.findById(jobCardId)
                .orElseThrow(() -> new RuntimeException("Error: Job Card not found."));

        if (jobCard.getJobStatus() != JobStatus.ON_HOLD) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Job is not on HOLD."));
        }

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Error: Customer not found."));

        // Update Vehicle Ownership
        Vehicle vehicle = jobCard.getVehicle();
        vehicle.setUser(customer);
        vehicle.setVehicleCondition(VehicleCondition.NON_DRIVABLE); // Assuming it's now a normal repair job
        vehicleRepository.save(vehicle);

        // Update Job Card
        jobCard.setUser(customer);
        jobCard.setJobStatus(JobStatus.PENDING); // Move to normal flow
        jobCardRepository.save(jobCard);

        return ResponseEntity.ok(new MessageResponse("Customer linked successfully. Job is now PENDING."));
    }

    // 3. Mark as Abandoned
    @PostMapping("/abandon/{jobCardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> markAsAbandoned(@PathVariable Long jobCardId) {
        JobCard jobCard = jobCardRepository.findById(jobCardId)
                .orElseThrow(() -> new RuntimeException("Error: Job Card not found."));

        jobCard.setJobStatus(JobStatus.ABANDONED);
        jobCardRepository.save(jobCard);

        return ResponseEntity.ok(new MessageResponse("Job marked as ABANDONED."));
    }
}
