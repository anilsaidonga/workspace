package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.User;
import com.automotiveserviceplatform.enums.AccountStatus;
import com.automotiveserviceplatform.enums.UserRole;
import com.automotiveserviceplatform.payload.request.LoginRequest;
import com.automotiveserviceplatform.payload.request.OtpVerificationRequest;
import com.automotiveserviceplatform.payload.request.PhoneNumberRequest;
import com.automotiveserviceplatform.payload.response.JwtResponse;
import com.automotiveserviceplatform.payload.response.MessageResponse;
import com.automotiveserviceplatform.repository.UserRepository;
import com.automotiveserviceplatform.security.jwt.JwtUtils;
import com.automotiveserviceplatform.security.services.UserDetailsImpl;
import com.automotiveserviceplatform.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OtpService otpService;

    // --- STAFF LOGIN (Password) ---
    // Allowed for: SUPER_ADMIN, ADMIN, MECHANIC
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Pre-check: Ensure user exists and is NOT a customer
        User user = userRepository.findByPhoneNumber(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        if (user.getRole() == UserRole.CUSTOMER) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Customers must use OTP login."));
        }
        
        if (user.getStatus() != AccountStatus.VERIFIED && user.getStatus() != AccountStatus.PROVISIONAL) {
             return ResponseEntity.badRequest().body(new MessageResponse("Error: Account is disabled."));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    // --- CUSTOMER LOGIN (OTP) ---
    // Allowed for: CUSTOMER only
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody PhoneNumberRequest request) {
        // Check if user exists and is NOT a customer (i.e., is Staff)
        userRepository.findByPhoneNumber(request.getPhoneNumber()).ifPresent(user -> {
            if (user.getRole() != UserRole.CUSTOMER) {
                throw new RuntimeException("Staff members must use Password Login.");
            }
        });

        otpService.generateOtp(request.getPhoneNumber());
        return ResponseEntity.ok(new MessageResponse("OTP sent successfully!"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        if (!otpService.validateOtp(request.getPhoneNumber(), request.getOtp())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid or expired OTP."));
        }

        // OTP is valid, find or create user
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseGet(() -> {
                    // Create new CUSTOMER
                    User newUser = new User();
                    newUser.setPhoneNumber(request.getPhoneNumber());
                    newUser.setName("Customer");
                    newUser.setRole(UserRole.CUSTOMER);
                    newUser.setStatus(AccountStatus.VERIFIED);
                    // Generate a secure random password for internal Spring Security use
                    newUser.setPassword(encoder.encode(UUID.randomUUID().toString()));
                    return userRepository.save(newUser);
                });

        // Strict Check: Only Customers can use OTP login
        if (user.getRole() != UserRole.CUSTOMER) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Staff members must use Password Login."));
        }
        
        if (user.getStatus() != AccountStatus.VERIFIED && user.getStatus() != AccountStatus.PROVISIONAL) {
             return ResponseEntity.badRequest().body(new MessageResponse("Error: Account is disabled."));
        }

        // Authenticate
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                UserDetailsImpl.build(user), null, UserDetailsImpl.build(user).getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
}
