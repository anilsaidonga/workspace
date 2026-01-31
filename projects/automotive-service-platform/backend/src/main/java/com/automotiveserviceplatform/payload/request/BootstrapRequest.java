package com.automotiveserviceplatform.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BootstrapRequest {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;
}
