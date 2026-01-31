package com.automotiveserviceplatform.payload.request;

import com.automotiveserviceplatform.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String name;

    @NotNull
    private UserRole role;

    private String password; // Optional for CUSTOMER, Required for Staff
}
