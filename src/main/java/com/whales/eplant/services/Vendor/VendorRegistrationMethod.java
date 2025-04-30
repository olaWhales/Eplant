package com.whales.eplant.services.Vendor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whales.eplant.data.model.Mc;
import com.whales.eplant.data.model.Role;
import com.whales.eplant.data.model.Users;
import com.whales.eplant.data.model.Vendor;
import com.whales.eplant.data.repository.McRepository;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.data.repository.VendorRepository;
import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@Slf4j
@AllArgsConstructor
public class VendorRegistrationMethod implements VendorRegistration{
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final VendorRepository vendorRepository;
    private final McRepository mcRepository;

//@Transactional
public VendorResponse vendorRegistration(VendorRequest request) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    log.info("Registering vendor for user: {}", username);

    Users user = userRepository.findByEmailWithVendors(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    Vendor vendor = Vendor.builder()
            .price(request.getPrice())
            .description(request.getDescription())
            .bonus(request.getBonus())
            .availability(request.isAvailability())
            .role(request.getRole())
            .user(user)
            .build();

    vendor = vendorRepository.save(vendor);

    if (request.getRole() == Role.MC) {
        Map<String, Object> attrs = request.getRoleAttributes();
        Mc mc = Mc.builder()
                .dressCodeIncluded((Boolean) attrs.get("dressCodeIncluded"))
                .languageOptions((String) attrs.get("languageOptions"))
                .performanceDuration((String) attrs.get("performanceDuration"))
                .eventTypeSpecialist((String) attrs.get("eventTypeSpecialist"))
                .vendor(vendor)
                .build();
        mcRepository.save(mc);
    } // Add else-if for other roles (e.g., DJ)

    log.info("Vendor saved: {} ({})", vendor.getDescription(), vendor.getRole());

    return VendorResponse.builder()
            .message("Registration successful")
            .build();
}
}
