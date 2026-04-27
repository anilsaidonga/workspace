package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.Payment;
import com.automotiveserviceplatform.enums.JobStatus;
import com.automotiveserviceplatform.enums.UserRole;
import com.automotiveserviceplatform.repository.JobCardRepository;
import com.automotiveserviceplatform.repository.PaymentRepository;
import com.automotiveserviceplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobCardRepository jobCardRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // User Counts
        long adminCount = userRepository.countByRole(UserRole.ADMIN);
        long mechanicCount = userRepository.countByRole(UserRole.MECHANIC);
        long customerCount = userRepository.countByRole(UserRole.CUSTOMER);

        stats.put("totalAdmins", adminCount);
        stats.put("totalMechanics", mechanicCount);
        stats.put("totalCustomers", customerCount);

        // Active Jobs
        long activeJobs = jobCardRepository.countByJobStatusNot(JobStatus.COMPLETED);
        stats.put("activeJobs", activeJobs);

        // Revenue (Simple sum of all payments for now)
        List<Payment> payments = paymentRepository.findAll();
        BigDecimal totalRevenue = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.put("totalRevenue", totalRevenue);

        return ResponseEntity.ok(stats);
    }
}
