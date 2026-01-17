package com.automotiveserviceplatform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estimates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_card_id")
    @JsonIgnore
    private JobCard jobCard;

    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabourItem> labourItems = new ArrayList<>();

    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartItem> partItems = new ArrayList<>();

    private BigDecimal taxes;

    private BigDecimal totalAmount;
    
    private boolean approved;
    
    // Helper method to add labour item
    public void addLabourItem(LabourItem item) {
        labourItems.add(item);
        item.setEstimate(this);
    }

    // Helper method to add part item
    public void addPartItem(PartItem item) {
        partItems.add(item);
        item.setEstimate(this);
    }
}
