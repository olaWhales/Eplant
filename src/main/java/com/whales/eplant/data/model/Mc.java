package com.whales.eplant.data.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean dressCodeIncluded;
    private String languageOptions;
    private String performanceDuration;
    private String eventTypeSpecialist;

    @OneToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
}