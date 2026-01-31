package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.enums.AccountStatus;
import com.automotiveserviceplatform.enums.UserRole;
import com.automotiveserviceplatform.payload.request.CreateUserRequest;
import com.automotiveserviceplatform.payload.response.MessageResponse;
import com.automotiveserviceplatform.repository.UserRepository;
import com.automotiveserviceplatform.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/create")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        String currentUserRole = currentUser.getAuthorities().iterator().next().getAuthority(); // e.g., ROLE_ADMIN

        // Authorization Logic
        if (request.getRole() == UserRole.SUPER_ADMIN) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Cannot create SUPER_ADMIN manually."));
        }

        if (currentUserRole.equals("ROLE_ADMIN")) {
            if (request.getRole() == UserRole.ADMIN) {
                return ResponseEntity.status(403).body(new MessageResponse("Error: ADMIN cannot create another ADMIN."));
            }
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone number already exists."));
        }

        User user = new User();
        user.setPhoneNumber(request.getPhoneNumber());
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setStatus(AccountStatus.VERIFIED);

        if (request.getRole() == UserRole.CUSTOMER) {
            // Customers don't need a password for login, but we set a dummy one
            user.setPassword(encoder.encode(UUID.randomUUID().toString()));
        } else {
            // Staff must have a password
            if (request.getPassword() == null || request.getPassword().isBlank()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Password is required for staff users."));
            }
            user.setPassword(encoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User created successfully!"));
    }
}
