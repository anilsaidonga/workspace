package com.automotiveserviceplatform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "part_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // Eager fetch for part details
    @JoinColumn(name = "part_id")
    private Part part;

    private int quantity;

    private BigDecimal cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id")
    @JsonIgnore
    private Estimate estimate;
}
