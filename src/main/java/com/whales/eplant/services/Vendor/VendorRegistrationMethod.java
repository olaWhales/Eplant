package com.whales.eplant.services.Vendor;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.*;
import com.whales.eplant.dto.request.mc.McRequest;
import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import com.whales.eplant.utility.Utility;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.whales.eplant.data.model.Mc;
import com.whales.eplant.data.model.Role;
import com.whales.eplant.data.model.Users;
import com.whales.eplant.data.model.Vendor;

@Service
@Slf4j
@AllArgsConstructor
public class VendorRegistrationMethod implements VendorRegistration {

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final McRepository mcRepository;
    private final DjRepository djRepository;


    @Transactional
    public VendorResponse vendorRegistration(VendorRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Registering vendor for user: {}", username);

        Users user = userRepository.findByEmailWithVendors(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (request.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
// Validate roleAttributes
        if (request.getRoleAttributes() != null && request.getRoleAttributes().isEmpty()) {
            log.warn("roleAttributes is empty; setting to null");
            request.setRoleAttributes(null); // Treat empty map as null
        }
//
        Vendor vendor = Vendor.builder()
                .price(request.getPrice())
                .description(request.getDescription())
                .bonus(request.getBonus())
                .availability(request.isAvailability())
                .role(request.getRole())
                .user(user)
                .build();

        // Set roleAttributes as JSON string
        if (request.getRoleAttributes() != null) {
            vendor.setRoleAttributesMap(request.getRoleAttributes());
        }

        vendor = vendorRepository.save(vendor);

        if (request.getRole() == Role.MC) {
            // Assuming VendorRequest includes MC-specific fields or a separate McRequest is passed
//            McRequest mcRequest = new McRequest(); // Replace with actual data source
//            if (mcRequest.getEventTypeSpecialist() == null) {
//                throw new IllegalArgumentException("eventTypeSpecialist is required for MC role");
//            }
//            Mc mc = Mc.builder()
//                    .eventTypeSpecialist(mcRequest.getEventTypeSpecialist())
//                    .performanceDuration(mcRequest.getPerformanceDuration())
//                    .dressCodeIncluded(mcRequest.isDressCodeIncluded())
//                    .languageOptions(mcRequest.getLanguageOptions())
//                    .vendor(vendor)
//                    .build();

            Map<String, Object> roleAttributes = request.getRoleAttributes();
//            if (roleAttributes == null) {
//                throw new IllegalArgumentException("roleAttributes required for MC role");
//            }
//            // Validate required keys
//            if (!roleAttributes.containsKey("dressCodeIncluded") || !(roleAttributes.get("dressCodeIncluded") instanceof Boolean)) {
//                throw new IllegalArgumentException("dressCodeIncluded must be a boolean");
//            }
//            if (!roleAttributes.containsKey("languageOptions") || !(roleAttributes.get("languageOptions") instanceof String)) {
//                throw new IllegalArgumentException("languageOptions must be a string");
//            }
//            if (!roleAttributes.containsKey("performanceDuration") || !(roleAttributes.get("performanceDuration") instanceof String)) {
//                throw new IllegalArgumentException("performanceDuration must be a string");
//            }
//            if (!roleAttributes.containsKey("eventTypeSpecialist") || !(roleAttributes.get("eventTypeSpecialist") instanceof String)) {
//                throw new IllegalArgumentException("eventTypeSpecialist must be a string");
//            }

            Mc mc = Mc.builder()
                    .dressCodeIncluded((Boolean) roleAttributes.get("dressCodeIncluded"))
                    .languageOptions((String) roleAttributes.get("languageOptions"))
                    .performanceDuration((String) roleAttributes.get("performanceDuration"))
                    .eventTypeSpecialist((String) roleAttributes.get("eventTypeSpecialist"))
                    .vendor(vendor)
                    .build();

            mcRepository.save(mc);
        } else if (request.getRole() == Role.DJ) {

            Map<String, Object> roleAttributes = request.getRoleAttributes();
//            Object genresObj = roleAttributes.get("musicGenres");
            List<String>musicGenres = Utility.safeCastStringList(roleAttributes.get("musicGenres"));

            Dj dj = Dj.builder()
                    .lightingIncluded((Boolean) roleAttributes.getOrDefault("lightingIncluded", false))
                    .numberOfSpeakers((Integer) roleAttributes.getOrDefault("numberOfSpeakers", 0))
                    .performanceDuration((String) roleAttributes.getOrDefault("performanceDuration", ""))
                    .numberOfMicrophones((Integer) roleAttributes.getOrDefault("numberOfMicrophones", 0))
                    .musicGenres(musicGenres)
                    .vendor(vendor)
                    .build();
        }
        log.info("Vendor saved: {} ({})", vendor.getDescription(), vendor.getRole());

        return VendorResponse.builder()
                .message("Registration successful")
                .build();
    }


//
//    @Transactional
//public VendorResponse vendorRegistration(VendorRequest request) {
//    String username = SecurityContextHolder.getContext().getAuthentication().getName();
//    log.info("Registering vendor for user: {}", username);
//
//    Users user = userRepository.findByEmailWithVendors(username)
//            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//
//    if (request.getRole() == null) {
//        throw new IllegalArgumentException("Role is required");
//    }
//
//    Vendor vendor = Vendor.builder()
//            .price(request.getPrice())
//            .description(request.getDescription())
//            .bonus(request.getBonus())
//            .availability(request.isAvailability())
//            .role(request.getRole())
//            .user(user)
//            .build();
//
//    vendor = vendorRepository.save(vendor);
//
//    if (request.getRole() == Role.MC) {
//        McRequest mcRequest = new McRequest(); // Fix: Provide actual MC data
//        Mc mc = Mc.builder()
//                .eventTypeSpecialist(mcRequest.getEventTypeSpecialist())
//                .performanceDuration(mcRequest.getPerformanceDuration())
//                .dressCodeIncluded(mcRequest.isDressCodeIncluded())
//                .languageOptions(mcRequest.getLanguageOptions())
//                .vendor(vendor)
//                .build();
//        mcRepository.save(mc);
//    } else if (request.getRole() == Role.DJ) {
//        Dj dj = new Dj(); // Fix: Complete DJ logic
//        dj.setVendor(vendor);
//        djRepository.save(dj); // Assumes DjRepository exists
//    }
//
//    log.info("Vendor saved: {} ({})", vendor.getDescription(), vendor.getRole());
//
//    return VendorResponse.builder()
//            .message("Registration successful")
////            .vendorId(vendor.getId())
//            .build();
//}
//
}