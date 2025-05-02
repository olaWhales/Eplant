package com.whales.eplant.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Caterer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    private List<String> menuOptions;
    private int numberOfMeals;
    private boolean offersTasting;
    @ElementCollection
    private List<String> dietaryConsiderations;
    private boolean deliveryIncluded;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
}
