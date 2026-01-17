package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.Appointment;
import com.automotiveserviceplatform.entity.JobCard;
import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.entity.Vehicle;
import com.automotiveserviceplatform.enums.AppointmentStatus;
import com.automotiveserviceplatform.enums.JobStatus;
import com.automotiveserviceplatform.enums.JobType;
import com.automotiveserviceplatform.payload.request.JobCardRequest;
import com.automotiveserviceplatform.payload.response.MessageResponse;
import com.automotiveserviceplatform.repository.AppointmentRepository;
import com.automotiveserviceplatform.repository.JobCardRepository;
import com.automotiveserviceplatform.repository.UserRepository;
import com.automotiveserviceplatform.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/job-cards")
public class JobCardController {

    @Autowired
    JobCardRepository jobCardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @PostMapping("/walk-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createWalkInJobCard(@Valid @RequestBody JobCardRequest jobCardRequest) {
        User user = userRepository.findById(jobCardRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        Vehicle vehicle = vehicleRepository.findById(jobCardRequest.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Error: Vehicle not found."));

        if (!vehicle.getUser().getId().equals(user.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Vehicle does not belong to this user."));
        }

        JobCard jobCard = new JobCard();
        jobCard.setUser(user);
        jobCard.setVehicle(vehicle);
        jobCard.setJobType(jobCardRequest.getJobType());
        jobCard.setJobStatus(JobStatus.PENDING);

        jobCardRepository.save(jobCard);

        return ResponseEntity.ok(new MessageResponse("Job Card created successfully!"));
    }

    @PostMapping("/check-in/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> checkInAppointment(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Error: Appointment not found."));

        if (appointment.getStatus() == AppointmentStatus.CHECKED_IN) {
             return ResponseEntity.badRequest().body(new MessageResponse("Error: Appointment already checked-in."));
        }

        appointment.setStatus(AppointmentStatus.CHECKED_IN);
        appointmentRepository.save(appointment);

        // Auto-create Job Card
        JobCard jobCard = new JobCard();
        jobCard.setUser(appointment.getUser());
        jobCard.setVehicle(appointment.getVehicle());
        jobCard.setJobType(JobType.WORKSHOP); // Default to Workshop for appointments
        jobCard.setJobStatus(JobStatus.PENDING);

        jobCardRepository.save(jobCard);

        return ResponseEntity.ok(new MessageResponse("Appointment checked-in and Job Card created!"));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MECHANIC')")
    public ResponseEntity<?> updateJobStatus(@PathVariable Long id, @RequestParam JobStatus status) {
        JobCard jobCard = jobCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Job Card not found."));

        jobCard.setJobStatus(status);
        jobCardRepository.save(jobCard);

        return ResponseEntity.ok(new MessageResponse("Job status updated successfully!"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MECHANIC')")
    public ResponseEntity<List<JobCard>> getAllJobCards() {
        List<JobCard> jobCards = jobCardRepository.findAll();
        return ResponseEntity.ok(jobCards);
    }
}
