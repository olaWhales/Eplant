package com.whales.eplant.data.model;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Decorator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    private List<String> themeOptions;
    private int numberOfVenues;
    private boolean lightingIncluded;
    private boolean flowersIncluded;
    private boolean customDesign;
}
