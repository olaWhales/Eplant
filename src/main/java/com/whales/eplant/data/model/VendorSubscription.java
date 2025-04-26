package com.whales.eplant.data.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VendorSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String event;
    private String vendor;
    private String subscriptionStatus;
    private String subscription;

    @OneToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendors;
}