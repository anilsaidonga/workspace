package com.automotiveserviceplatform.payload.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LabourItemRequest {
    private String description;
    private BigDecimal cost;
}
