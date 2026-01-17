package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.*;
import com.automotiveserviceplatform.payload.request.EstimateRequest;
import com.automotiveserviceplatform.payload.response.MessageResponse;
import com.automotiveserviceplatform.repository.EstimateRepository;
import com.automotiveserviceplatform.repository.JobCardRepository;
import com.automotiveserviceplatform.repository.PartRepository;
import com.automotiveserviceplatform.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/estimates")
public class EstimateController {

    private static final Logger logger = LoggerFactory.getLogger(EstimateController.class);

    @Autowired
    EstimateRepository estimateRepository;

    @Autowired
    JobCardRepository jobCardRepository;

    @Autowired
    PartRepository partRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEstimate(@Valid @RequestBody EstimateRequest estimateRequest) {
        logger.info("Received createEstimate request for JobCard ID: {}", estimateRequest.getJobCardId());

        JobCard jobCard = jobCardRepository.findById(estimateRequest.getJobCardId())
                .orElseThrow(() -> new RuntimeException("Error: Job Card not found."));

        Estimate estimate = new Estimate();
        estimate.setJobCard(jobCard);
        estimate.setTaxes(estimateRequest.getTaxes());
        estimate.setApproved(false);

        BigDecimal totalAmount = BigDecimal.ZERO;

        // Add Labour Items
        if (estimateRequest.getLabourItems() != null) {
            for (var itemReq : estimateRequest.getLabourItems()) {
                LabourItem item = new LabourItem();
                item.setDescription(itemReq.getDescription());
                item.setCost(itemReq.getCost());
                estimate.addLabourItem(item);
                totalAmount = totalAmount.add(itemReq.getCost());
            }
        }

        // Add Part Items
        if (estimateRequest.getPartItems() != null) {
            for (var itemReq : estimateRequest.getPartItems()) {
                Part part = partRepository.findById(itemReq.getPartId())
                        .orElseThrow(() -> new RuntimeException("Error: Part not found with id: " + itemReq.getPartId()));
                
                PartItem item = new PartItem();
                item.setPart(part);
                item.setQuantity(itemReq.getQuantity());
                item.setCost(itemReq.getCost());
                estimate.addPartItem(item);
                totalAmount = totalAmount.add(itemReq.getCost().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            }
        }

        // Add Taxes
        if (estimateRequest.getTaxes() != null) {
            totalAmount = totalAmount.add(estimateRequest.getTaxes());
        }

        estimate.setTotalAmount(totalAmount);
        estimateRepository.save(estimate);

        return ResponseEntity.ok(new MessageResponse("Estimate created successfully!"));
    }

    @GetMapping("/job-card/{jobCardId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('MECHANIC')")
    public ResponseEntity<?> getEstimateByJobCard(@PathVariable Long jobCardId) {
        Estimate estimate = estimateRepository.findByJobCardId(jobCardId)
                .orElseThrow(() -> new RuntimeException("Error: Estimate not found for this Job Card."));
        
        // Security check for customer
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        boolean isCustomer = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));

        if (isCustomer && !estimate.getJobCard().getUser().getId().equals(userDetails.getId())) {
             return ResponseEntity.status(403).body(new MessageResponse("Error: You can only view estimates for your own jobs."));
        }

        return ResponseEntity.ok(estimate);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> approveEstimate(@PathVariable Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Estimate not found."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!estimate.getJobCard().getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).body(new MessageResponse("Error: You can only approve estimates for your own jobs."));
        }

        estimate.setApproved(true);
        estimateRepository.save(estimate);

        return ResponseEntity.ok(new MessageResponse("Estimate approved successfully!"));
    }
    
    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> rejectEstimate(@PathVariable Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Estimate not found."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!estimate.getJobCard().getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).body(new MessageResponse("Error: You can only reject estimates for your own jobs."));
        }

        estimate.setApproved(false);
        // In a real app, we might want to store a rejection reason or status
        estimateRepository.save(estimate);

        return ResponseEntity.ok(new MessageResponse("Estimate rejected."));
    }
}
