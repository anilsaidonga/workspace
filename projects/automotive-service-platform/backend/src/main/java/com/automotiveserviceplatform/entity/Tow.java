package com.automotiveserviceplatform.entity;

import com.automotiveserviceplatform.enums.TowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickupLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TowStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_card_id")
    private JobCard jobCard;
}
