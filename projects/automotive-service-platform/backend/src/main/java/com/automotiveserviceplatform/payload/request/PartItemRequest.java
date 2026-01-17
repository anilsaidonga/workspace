package com.automotiveserviceplatform.payload.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PartItemRequest {
    private Long partId;
    private int quantity;
    private BigDecimal cost;
}
