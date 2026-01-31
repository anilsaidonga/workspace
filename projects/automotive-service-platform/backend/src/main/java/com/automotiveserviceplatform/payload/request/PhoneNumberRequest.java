package com.automotiveserviceplatform.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PhoneNumberRequest {
    @NotBlank
    private String phoneNumber;
}
