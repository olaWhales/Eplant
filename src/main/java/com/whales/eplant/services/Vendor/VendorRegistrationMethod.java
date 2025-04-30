package com.whales.eplant.services.Vendor;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.CatererRepository;
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

    @Transactional
    public VendorResponse vendorRegistration(VendorRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Registering vendor for user: {}", username);

        Users user = userRepository.findByEmailWithVendors(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (request.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }

        Vendor vendor = Vendor.builder()
                .price(request.getPriceAsBigDecimal())
//                .price(request.getPrice() != null ? new BigDecimal(request.getPrice().toString()) : null)
                .description(request.getDescription())
                .bonus(request.getBonusBigDecimal())
//                .bonus(request.getBonus() != null ? new BigDecimal(String.valueOf(request.getBonus())) : null)
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
            Map<String, Object> roleAttributes = request.getRoleAttributes();
            if (roleAttributes == null) {
                throw new IllegalArgumentException("roleAttributes required for MC role");
            }
            // Validate required keys
            if (!roleAttributes.containsKey("dressCodeIncluded") || !(roleAttributes.get("dressCodeIncluded") instanceof Boolean)) {
                throw new IllegalArgumentException("dressCodeIncluded must be a boolean");
            }
            if (!roleAttributes.containsKey("languageOptions") || !(roleAttributes.get("languageOptions") instanceof String)) {
                throw new IllegalArgumentException("languageOptions must be a string");
            }
            if (!roleAttributes.containsKey("performanceDuration") || !(roleAttributes.get("performanceDuration") instanceof String)) {
                throw new IllegalArgumentException("performanceDuration must be a string");
            }
            if (!roleAttributes.containsKey("eventTypeSpecialist") || !(roleAttributes.get("eventTypeSpecialist") instanceof String)) {
                throw new IllegalArgumentException("eventTypeSpecialist must be a string");
            }

            Mc mc = Mc.builder()
                    .dressCodeIncluded((Boolean) roleAttributes.get("dressCodeIncluded"))
                    .languageOptions((String) roleAttributes.get("languageOptions"))
                    .performanceDuration((String) roleAttributes.get("performanceDuration"))
                    .eventTypeSpecialist((String) roleAttributes.get("eventTypeSpecialist"))
                    .vendor(vendor)
                    .build();
            mcRepository.save(mc);
        }

        log.info("Vendor saved: {} ({})", vendor.getDescription(), vendor.getRole());

        return VendorResponse.builder()
                .message("Registration successful")
                .build();
    }
}
