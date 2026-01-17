package com.automotiveserviceplatform.payload.request;

import com.automotiveserviceplatform.enums.FuelType;
import com.automotiveserviceplatform.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HoldVehicleRequest {
    @NotBlank
    private String vehicleNumber;

    @NotNull
    private VehicleType type;

    @NotNull
    private FuelType fuelType;
    
    private String towDriverPhone; // Optional contact for the tow driver
}
