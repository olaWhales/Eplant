package com.whales.eplant.services.Vendor;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.*;
import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import com.whales.eplant.utility.Utility;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
@AllArgsConstructor
public class VendorRegistrationMethod implements VendorRegistration {


    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final McRepository mcRepository;
    private final DjRepository djRepository;
    private final CatererRepository catererRepository ;

    @Transactional
    public VendorResponse vendorRegistration(VendorRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Registering vendor for user: {}", username);

        Users user = userRepository.findByEmailWithVendors(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (request.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }

        if (request.getRoleAttributes() != null && request.getRoleAttributes().isEmpty()) {
            log.warn("roleAttributes is empty; setting to null");
            request.setRoleAttributes(null);
        }

        Vendor vendor = Vendor.builder()
                .price(request.getPriceAsBigDecimal())
                .description(request.getDescription())
                .bonus(request.getBonusBigDecimal())
                .availability(request.isAvailability())
                .role(request.getRole())
                .user(user)
                .build();

        if (request.getRoleAttributes() != null) {
            vendor.setRoleAttributesMap(request.getRoleAttributes());
        }

        if (request.getRole() == Role.MC) {
            Map<String, Object> roleAttributes = request.getRoleAttributes();
            Mc mc = Mc.builder()
                    .dressCodeIncluded((Boolean) roleAttributes.get("dressCodeIncluded"))
                    .languageOptions((String) roleAttributes.get("languageOptions"))
                    .performanceDuration((String) roleAttributes.get("performanceDuration"))
                    .eventTypeSpecialist((String) roleAttributes.get("eventTypeSpecialist"))
                    .vendor(vendor)
                    .build();
            mcRepository.save(mc); // Saves Vendor via cascade
        } else if (request.getRole() == Role.DJ) {
            Map<String, Object> roleAttributes = request.getRoleAttributes();
            List<String> musicGenres = Utility.safeCastStringList(roleAttributes.get("musicGenres"));

            Dj dj = Dj.builder()
                    .lightingIncluded((Boolean) roleAttributes.getOrDefault("lightingIncluded", false))
                    .numberOfSpeakers((Integer) roleAttributes.getOrDefault("numberOfSpeakers", 0))
                    .performanceDuration((String) roleAttributes.getOrDefault("performanceDuration", ""))
                    .numberOfMicrophones((Integer) roleAttributes.getOrDefault("numberOfMicrophones", 0))
                    .musicGenres(musicGenres)
                    .vendor(vendor)
                    .build();
            djRepository.save(dj); // Saves Vendor via cascade
        } else if (request.getRole() == Role.CATERER) {
        Map<String, Object> roleAttributes = request.getRoleAttributes();
        List<String> menuOptions = Utility.safeCastStringList(roleAttributes.get("menuOptions"));

        Caterer caterer = Caterer.builder()
                .menuOptions(menuOptions)
                .deliveryIncluded(true)
                .offersTasting(true)
                .menuOptions(menuOptions)
                .vendor(vendor)
                .build();
        catererRepository.save(caterer);
    }
        else {
            vendorRepository.save(vendor); // Save Vendor directly for other roles
        }

        log.info("Vendor saved: {} ({})", vendor.getDescription(), vendor.getRole());
        return VendorResponse.builder()
                .message("Registration successful")
                .build();
    }
}