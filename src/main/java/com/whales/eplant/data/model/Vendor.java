package com.whales.eplant.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.whales.eplant.utility.EmptyMapSerializer;
import com.whales.eplant.utility.HashMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal bonus;

    private boolean availability;


    @Enumerated(EnumType.STRING)
    private Role role;

//    @Convert(converter = HashMapConverter.class)
//    @Column(columnDefinition = "json")
//    private Map<String, Object> roleAttributesMap;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role role;
//
//    /**
//     * Stores role-specific attributes as a JSON string in the database.
//     * Example: {"dressCodeIncluded": true, "languageOptions": "English, Spanish"}
//     */
//    @Column(columnDefinition = "text")
//    private String roleAttributes; // Changed from Role to String
//
//    /**
//     * Transient field to handle roleAttributes as a Map in memory for easier access.
//     * Not persisted to the database; converted to/from roleAttributes JSON string.
//     */
//    @Transient
////    @JsonUnwrapped  // This helps with proper serialization
////    @JsonSerialize
//    @JsonSerialize(using = EmptyMapSerializer.class)
//    private Map<String, Object> roleAttributesMap;
////    @Transient
////    private Map<String, Object> roleAttributesMap;
//
//    /**
//     * Converts a Map of role attributes to a JSON string for database storage.
//     * @param roleAttributesMap Map containing role-specific attributes.
//     */
//    public void setRoleAttributesMap(Map<String, Object> roleAttributesMap) {
//        this.roleAttributesMap = roleAttributesMap;
//        try {
//            this.roleAttributes = roleAttributesMap == null ? null : new ObjectMapper().writeValueAsString(roleAttributesMap);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to serialize roleAttributes", e);
//        }
//    }
//
//    /**
//     * Converts the stored JSON string to a Map for in-memory use.
//     * @return Map of role-specific attributes, or null if roleAttributes is null.
//     */
//    public Map<String, Object> getRoleAttributesMap() {
//        if (roleAttributesMap == null && roleAttributes != null) {
//            try {
//                roleAttributesMap = new ObjectMapper().readValue(roleAttributes, Map.class);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException("Failed to deserialize roleAttributes", e);
//            }
//        }
//        return roleAttributesMap;
//    }
}