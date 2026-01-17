package com.automotiveserviceplatform.payload.request;

import com.automotiveserviceplatform.enums.FuelType;
import com.automotiveserviceplatform.enums.VehicleCondition;
import com.automotiveserviceplatform.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleRequest {
    @NotBlank
    private String vehicleNumber;

    @NotNull
    private VehicleType type;

    @NotNull
    private FuelType fuelType;

    @NotNull
    private VehicleCondition condition;
}
