package com.whales.eplant.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Vendor> vendors;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Event> events;

    @OneToMany(mappedBy = "recipient")
    @JsonIgnore
    private List<Notification> notifications;
}