package com.whales.eplant.data.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

//    @OneToOne(mappedBy = "vendor", cascade = CascadeType.ALL)
//    private VendorSubscription subscription;

    private BigDecimal price;
    private String description;
    private BigDecimal bonus;
    private boolean availability;
    private Role role;

    @Column(columnDefinition = "text")
    private String roleAttributes;
}