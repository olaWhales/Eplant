package com.whales.eplant.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String languageOptions;
    private String eventTypeSpecialist;
    private String performanceDuration;
    private boolean dressCodeIncluded ;

}
