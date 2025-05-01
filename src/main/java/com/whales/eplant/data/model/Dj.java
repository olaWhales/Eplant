package com.whales.eplant.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int numberOfSpeakers;
    private int numberOfMicrophones;

    @ElementCollection
    private List<String> musicGenres = new ArrayList<>();
    private boolean lightingIncluded;
    private String performanceDuration;

    @OneToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
}
