package com.whales.eplant.services.Vendor;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.*;
import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class VendorRegistrationMethod implements VendorRegistration {

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final McRepository mcRepository;
    private final DjRepository djRepository;
    private final CatererRepository catererRepository;

    @Transactional
    public VendorResponse vendorRegistration(VendorRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Registering vendor for user: {}", username);

        Users user = userRepository.findByEmailWithVendors(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        if (request.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }

        Vendor vendor = Vendor.builder()
                .price(request.getPriceAsBigDecimal())
                .description(request.getDescription())
                .bonus(request.getBonusBigDecimal())
                .availability(request.isAvailability())
                .role(request.getRole())
                .user(user)
                .build();

        // Always set roleAttributesMap, even if input is null or empty
//        Map<String, Object> roleAttributes = (request.getRoleAttributes() == null || request.getRoleAttributes().isEmpty())
//                ? new HashMap<>()
//                : request.getRoleAttributes();
//        vendor.setRoleAttributesMap(roleAttributes);
        if(request.getRole() == Role.DJ){
            Dj dj = Dj.builder()
                    .performanceDuration(request.getPerformanceDuration())
                    .numberOfMicrophones(request.getNumberOfMicrophones())
                    .lightingIncluded(request.isLightingIncluded())
                    .musicGenres(request.getMusicGenres())
                    .numberOfSpeakers(request.getNumberOfSpeakers())
                    .build();
            djRepository.save(dj);
        } else if (request.getRole() == Role.MC) {
            Mc mc = Mc.builder()
                    .performanceDuration(request.getPerformanceDuration())
                    .dressCodeIncluded(request.isDressCodeIncluded())
                    .eventTypeSpecialist(request.getEventTypeSpecialist())
                    .languageOptions(request.getLanguageOptions())
                    .eventTypeSpecialist(request.getEventTypeSpecialist())
                    .build();
            mcRepository.save(mc);
        }

//        if (request.getRole() == Role.MC) {
//            Mc mc = Mc.builder()
//                    .dressCodeIncluded((Boolean) roleAttributes.getOrDefault("dressCodeIncluded", false))
//                    .languageOptions((String) roleAttributes.getOrDefault("languageOptions", ""))
//                    .performanceDuration((String) roleAttributes.getOrDefault("performanceDuration", ""))
//                    .eventTypeSpecialist((String) roleAttributes.getOrDefault("eventTypeSpecialist", ""))
//                    .vendor(vendor)
//                    .build();
//            mcRepository.save(mc);
//        } else if (request.getRole() == Role.DJ) {
//            List<String> musicGenres = Utility.safeCastStringList(roleAttributes.getOrDefault("musicGenres", List.of()));
//            Dj dj = Dj.builder()
//                    .lightingIncluded((Boolean) roleAttributes.getOrDefault("lightingIncluded", false))
//                    .numberOfSpeakers((Integer) roleAttributes.getOrDefault("numberOfSpeakers", 0))
//                    .performanceDuration((String) roleAttributes.getOrDefault("performanceDuration", ""))
//                    .numberOfMicrophones((Integer) roleAttributes.getOrDefault("numberOfMicrophones", 0))
//                    .musicGenres(musicGenres)
//                    .vendor(vendor)
//                    .build();
//            djRepository.save(dj);
//        } else if (request.getRole() == Role.CATERER) {
//            List<String> menuOptions = Utility.safeCastStringList(roleAttributes.getOrDefault("menuOptions", List.of()));
//            Caterer caterer = Caterer.builder()
//                    .menuOptions(menuOptions)
//                    .deliveryIncluded(true)
//                    .offersTasting(true)
//                    .vendor(vendor)
//                    .build();
//            catererRepository.save(caterer);
//        } else {
//            vendorRepository.save(vendor);
//        }

        log.info("Vendor saved: {} ({})", vendor.getDescription(), vendor.getRole());
        return VendorResponse.builder()
                .message("Registration successful")
                .build();
    }
}