package com.automotiveserviceplatform.payload.request;

import com.automotiveserviceplatform.enums.JobType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobCardRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long vehicleId;

    @NotNull
    private JobType jobType;
}
