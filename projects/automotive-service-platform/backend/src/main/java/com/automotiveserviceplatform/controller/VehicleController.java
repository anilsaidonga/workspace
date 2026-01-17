package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.entity.Vehicle;
import com.automotiveserviceplatform.payload.request.VehicleRequest;
import com.automotiveserviceplatform.payload.response.MessageResponse;
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

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Vehicle>> getMyVehicles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        List<Vehicle> vehicles = vehicleRepository.findByUserId(userDetails.getId());
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addVehicle(@Valid @RequestBody VehicleRequest vehicleRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        if (vehicleRepository.existsByVehicleNumber(vehicleRequest.getVehicleNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Vehicle number already exists!"));
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleRequest.getVehicleNumber());
        vehicle.setType(vehicleRequest.getType());
        vehicle.setFuelType(vehicleRequest.getFuelType());
        vehicle.setVehicleCondition(vehicleRequest.getCondition());
        vehicle.setUser(user);

        vehicleRepository.save(vehicle);

        return ResponseEntity.ok(new MessageResponse("Vehicle added successfully!"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Vehicle not found."));

        if (!vehicle.getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).body(new MessageResponse("Error: You can only delete your own vehicles."));
        }

        vehicleRepository.delete(vehicle);
        return ResponseEntity.ok(new MessageResponse("Vehicle deleted successfully!"));
    }
}
