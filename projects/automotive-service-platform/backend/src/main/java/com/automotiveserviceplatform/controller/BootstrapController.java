package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.enums.AccountStatus;
import com.automotiveserviceplatform.enums.UserRole;
import com.automotiveserviceplatform.payload.request.BootstrapRequest;
import com.automotiveserviceplatform.payload.response.MessageResponse;
import com.automotiveserviceplatform.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/bootstrap")
public class BootstrapController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/super-admin")
    public ResponseEntity<?> createSuperAdmin(@Valid @RequestBody BootstrapRequest request) {
        // 1. Check if any SUPER_ADMIN exists
        boolean superAdminExists = userRepository.findAll().stream()
                .anyMatch(user -> user.getRole() == UserRole.SUPER_ADMIN);

        if (superAdminExists) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: System already initialized. SUPER_ADMIN exists."));
        }

        // 2. Create SUPER_ADMIN
        User user = new User();
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(UserRole.SUPER_ADMIN);
        user.setStatus(AccountStatus.VERIFIED);
        user.setName("Super Admin"); // Default name

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("System initialized successfully. SUPER_ADMIN created."));
    }
}
