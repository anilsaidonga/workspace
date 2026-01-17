package com.automotiveserviceplatform.payload.request;

import com.automotiveserviceplatform.enums.AppointmentType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    @NotNull
    private Long vehicleId;

    @NotNull
    private AppointmentType appointmentType;

    @NotNull
    private LocalDateTime timeSlot;
}
