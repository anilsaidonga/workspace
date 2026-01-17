package com.automotiveserviceplatform.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartRequest {
    @NotBlank
    private String name;

    @NotNull
    private int quantity;
}
