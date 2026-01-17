package com.automotiveserviceplatform.payload.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EstimateRequest {
    private Long jobCardId;
    private List<LabourItemRequest> labourItems;
    private List<PartItemRequest> partItems;
    private BigDecimal taxes;
}
