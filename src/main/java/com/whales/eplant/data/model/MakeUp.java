package com.whales.eplant.data.model;

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
public class MakeUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    private List<String> makeupStyles;
    private int numberOfPeople;
    private boolean offersTrialSession;

    @ElementCollection
    private List<String> productsUsed;
    private String durationPerSession;
}
