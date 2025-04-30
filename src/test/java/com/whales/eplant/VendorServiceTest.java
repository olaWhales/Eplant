package com.whales.eplant;

import com.whales.eplant.data.model.Mc;
import com.whales.eplant.data.model.Role;
import com.whales.eplant.data.model.Users;
import com.whales.eplant.data.model.Vendor;
import com.whales.eplant.data.repository.McRepository;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.data.repository.VendorRepository;
import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import com.whales.eplant.services.Vendor.VendorRegistration;
import com.whales.eplant.services.Vendor.VendorRegistrationMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class VendorServiceTest {

    @Autowired
    private VendorRegistrationMethod vendorRegistrationMethod;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private  McRepository mcRepository;

    private Users user;

    @BeforeEach
    void setUp() {
        // Create and save user
        user = Users.builder()
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("hashedPassword")
                .enabled(true)
                .build();
        userRepository.save(user);

        // Mock authentication
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, null
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

//    @ParameterizedTest
//    @CsvSource({
//            "MC, Professional MC for weddings, Yoruba, true, 5hours, all",
//            "DJ, Professional DJ for events, House, false, 4hours, party"
//    })
//    void testRegisterVendor(String role, String description, String languageOptions, boolean dressCodeIncluded,
//                            String performanceDuration, String eventTypeSpecialist) {
//        // Create VendorRequest
//        VendorRequest request = VendorRequest.builder()
//                .price(BigDecimal.valueOf(100000.00))
//                .description(description)
//                .bonus(BigDecimal.valueOf(100.00))
//                .availability(true)
//                .role(Role.valueOf(role))
//                .roleAttributes(Map.of(
//                        "languageOptions", languageOptions,
//                        "dressCodeIncluded", dressCodeIncluded,
//                        "performanceDuration", performanceDuration,
//                        "eventTypeSpecialist", eventTypeSpecialist
//                ))
//                .build();
//
//        // Register vendor
//        VendorResponse response = vendorRegistrationMethod.vendorRegistration(request);
//
//        // Verify response
//        assertEquals("Registration successful", response.getMessage());
//
//        // Verify vendor in database
//        Vendor savedVendor = vendorRepository.findAll().stream()
//                .filter(v -> v.getUser().getEmail().equals(user.getEmail()))
//                .findFirst()
//                .orElseThrow(() -> new AssertionError("Vendor not found"));
//        assertEquals(Role.valueOf(role), savedVendor.getRole());
//        assertEquals(BigDecimal.valueOf(100000.00), savedVendor.getPrice());
//        assertEquals(description, savedVendor.getDescription());
//        assertEquals(true, savedVendor.getRoleAttributes().contains(languageOptions));
//    }
@ParameterizedTest
@CsvSource({
        "MC, Professional MC for weddings, Yoruba, true, 5hours, all",
        "DJ, Professional DJ for events, House, false, 4hours, party"
})
void testRegisterVendor(String role, String description, String languageOptions, boolean dressCodeIncluded,
                        String performanceDuration, String eventTypeSpecialist) {
    Map<String, Object> roleAttributes = role.equals("MC") ?
            Map.of(
                    "languageOptions", languageOptions,
                    "dressCodeIncluded", dressCodeIncluded,
                    "performanceDuration", performanceDuration,
                    "eventTypeSpecialist", eventTypeSpecialist
            ) :
            Map.of(
                    "musicGenres", languageOptions,
                    "equipmentProvided", dressCodeIncluded
            );

    VendorRequest request = VendorRequest.builder()
            .price(BigDecimal.valueOf(100000.00))
            .description(description)
            .bonus(BigDecimal.valueOf(100.00))
            .availability(true)
            .role(Role.valueOf(role))
            .roleAttributes(roleAttributes)
            .build();

    VendorResponse response = vendorRegistrationMethod.vendorRegistration(request);
    assertEquals("Registration successful", response.getMessage());

    Vendor savedVendor = vendorRepository.findAll().stream()
            .filter(v -> v.getUser().getEmail().equals(user.getEmail()))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Vendor not found"));
    assertEquals(Role.valueOf(role), savedVendor.getRole());

    if (role.equals("MC")) {
        Mc savedMc = mcRepository.findAll().stream()
                .filter(m -> m.getVendor().getId().equals(savedVendor.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Mc not found"));
        assertEquals(languageOptions, savedMc.getLanguageOptions());
        assertEquals(dressCodeIncluded, savedMc.isDressCodeIncluded());
    }
}
}