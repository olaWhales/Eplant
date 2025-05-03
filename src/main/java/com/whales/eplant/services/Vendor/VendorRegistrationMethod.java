package com.whales.eplant.services.Vendor;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.*;
import com.whales.eplant.dto.request.caterer.CatererAttributes;
import com.whales.eplant.dto.request.dj.DjAttributes;
import com.whales.eplant.dto.request.makeUp.MakeUpAttributes;
import com.whales.eplant.dto.request.mc.McAttributes;
import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import com.whales.eplant.services.Event.EventRegistrationMethod;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.whales.eplant.utility.Utility.USER_NOT_AUTHENTICATED_MESSAGE;

@Service
@RequiredArgsConstructor
public class VendorRegistrationMethod implements VendorRegistration {

    private static final Logger log = LoggerFactory.getLogger(VendorRegistrationMethod.class);
    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final McRepository mcRepository;
    private final DjRepository djRepository;
    private final CatererRepository catererRepository;
    private final MakeUpRepository makeUpRepository;

    @Transactional
    public VendorResponse vendorRegistration(VendorRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new IllegalArgumentException(USER_NOT_AUTHENTICATED_MESSAGE);
        }
        String username = authentication.getName();
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
        vendorRepository.save(vendor);

        log.info("this is the role{}", request.getRole());

        if(request.getRole() == Role.DJ || request.getDjAttributes() != null) {
            DjAttributes djAttributes = request.getDjAttributes();
            Dj dj = Dj.builder()
                    .performanceDuration(djAttributes.getPerformanceDuration())
                    .numberOfMicrophones(djAttributes.getNumberOfMicrophones())
                    .lightingIncluded(djAttributes.isLightingIncluded())
                    .musicGenres(djAttributes.getMusicGenres())
                    .numberOfSpeakers(djAttributes.getNumberOfSpeakers())
                    .build();
            djRepository.save(dj);
        } else if (request.getRole() == Role.MC || request.getMcAttributes() != null) {
            McAttributes mcAttributes = request.getMcAttributes();
            Mc mc = Mc.builder()
                    .performanceDuration(mcAttributes.getPerformanceDuration())
                    .dressCodeIncluded(mcAttributes.isDressCodeIncluded())
                    .eventTypeSpecialist(mcAttributes.getEventTypeSpecialist())
                    .languageOptions(mcAttributes.getLanguageOptions())
                    .eventTypeSpecialist(mcAttributes.getEventTypeSpecialist())
                    .build();
            mcRepository.save(mc);
        } else if (request.getRole() == Role.CATERER || request.getCatererAttributes() != null) {
            CatererAttributes catererAttributes = request.getCatererAttributes();
            Caterer caterer = Caterer.builder()
                    .menuOptions(catererAttributes.getThemeOptions())
                    .offersTasting(catererAttributes.isOfferTasting())
                    .deliveryIncluded(catererAttributes.isDeliveryIncluded())
                    .dietaryConsiderations(catererAttributes.getDietaryConsiderations())
                    .numberOfMeals(catererAttributes.getNumberOfMeals())
//                    .vendor(vendor)
                    .build();
            catererRepository.save(caterer);
        } else if (request.getRole() == Role.MAKE_UP || request.getMakeUpAttributes() != null) {
            MakeUpAttributes makeUpAttributes = request.getMakeUpAttributes();
            MakeUp makeUp = MakeUp.builder()
                    .makeupStyles(makeUpAttributes.getMakeupStyles())
                    .productsUsed(makeUpAttributes.getProductsUsed())
                    .durationPerSession(makeUpAttributes.getDurationPerSession())
                    .offersTrialSession(makeUpAttributes.isOffersTrialSession())
                    .numberOfPeople(makeUpAttributes.getNumberOfPeople())
                    .build();
            makeUpRepository.save(makeUp);
        } else if (request.getRole() == Role.DECORATOR || request.getDecoratorAttributes() != null) {

        }


        log.info("Vendor saved: {} ({})", vendor.getDescription(), vendor.getRole());
        return VendorResponse.builder()
                .message("Registration successful")
                .build();
    }
}
        // Always set roleAttributesMap, even if input is null or empty
//        Map<String, Object> roleAttributes = (request.getRoleAttributes() == null || request.getRoleAttributes().isEmpty())
//                ? new HashMap<>()
//                : request.getRoleAttributes();
//        vendor.setRoleAttributesMap(roleAttributes);
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
