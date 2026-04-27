package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.ServiceType;
import com.automotiveserviceplatform.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/service-types")
public class ServiceTypeController {

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @GetMapping
    public ResponseEntity<List<ServiceType>> getAllServiceTypes() {
        return ResponseEntity.ok(serviceTypeRepository.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createServiceType(@RequestBody ServiceType serviceType) {
        return ResponseEntity.ok(serviceTypeRepository.save(serviceType));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateServiceType(@PathVariable Long id, @RequestBody ServiceType details) {
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Type not found"));
        
        serviceType.setName(details.getName());
        serviceType.setBasePrice(details.getBasePrice());
        
        return ResponseEntity.ok(serviceTypeRepository.save(serviceType));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteServiceType(@PathVariable Long id) {
        serviceTypeRepository.deleteById(id);
        return ResponseEntity.ok("Service Type deleted successfully");
    }
}
