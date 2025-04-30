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
import com.whales.eplant.services.Vendor.VendorRegistrationMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendorRegistrationMethodTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VendorRepository vendorRepository;

    @Mock
    private McRepository mcRepository;

    @InjectMocks
    private VendorRegistrationMethod vendorRegistrationMethod;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private Users user;
    private VendorRequest request1;
    private VendorRequest request2;

    @BeforeEach
    void setUp() {
        // Mock the SecurityContext
        when(authentication.getName()).thenReturn("test@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock the user
        user = new Users();
        user.setEmail("test@example.com");

        // Create VendorRequest 1 (MC with wedding attributes)
        request1 = new VendorRequest();
        request1.setPrice(100.00);
        request1.setDescription("Wedding MC");
        request1.setBonus(200.00);
        request1.setAvailability(true);
        request1.setRole(Role.MC);
        Map<String, Object> roleAttributes1 = new HashMap<>();
        roleAttributes1.put("dressCodeIncluded", true);
        roleAttributes1.put("languageOptions", "Yoruba");
        roleAttributes1.put("performanceDuration", "5 hours");
        roleAttributes1.put("eventTypeSpecialist", "Weddings");
        request1.setRoleAttributes(roleAttributes1);

        // Create VendorRequest 2 (MC with corporate attributes)
        request2 = new VendorRequest();
        request2.setPrice(400.00);
        request2.setDescription("Corporate MC");
        request2.setBonus(30.00);
        request2.setAvailability(true);
        request2.setRole(Role.MC);
        Map<String, Object> roleAttributes2 = new HashMap<>();
        roleAttributes2.put("dressCodeIncluded", false);
        roleAttributes2.put("languageOptions", "English");
        roleAttributes2.put("performanceDuration", "3 hours");
        roleAttributes2.put("eventTypeSpecialist", "Corporate");
        request2.setRoleAttributes(roleAttributes2);
    }

    @Test
    void testToRegisterMcAsAVendor_Success_Wedding() {
        // Mock repository behavior
        when(userRepository.findByEmailWithVendors("test@example.com")).thenReturn(Optional.of(user));
        when(vendorRepository.save(any(Vendor.class))).thenAnswer(invocation -> {
            Vendor vendor = invocation.getArgument(0);
            vendor.setId(1L);
            return vendor;
        });
        when(mcRepository.save(any(Mc.class))).thenAnswer(invocation -> {
            Mc mc = invocation.getArgument(0);
            mc.setId(1L);
            return mc;
        });

        // Call the method
        VendorResponse response = vendorRegistrationMethod.vendorRegistration(request1);

        // Verify
        assertNotNull(response);
        assertEquals("Registration successful", response.getMessage());
        verify(userRepository).findByEmailWithVendors("test@example.com");
        verify(vendorRepository).save(any(Vendor.class));
        verify(mcRepository).save(argThat(mc ->
                mc.isDressCodeIncluded() &&
                        mc.getLanguageOptions().equals("Yoruba") &&
                        mc.getPerformanceDuration().equals("5 hours") &&
                        mc.getEventTypeSpecialist().equals("Weddings")
        ));
    }

    @Test
    void testToRegisterMcAsAVendor_Success_Corporate() {
        // Mock repository behavior
        when(userRepository.findByEmailWithVendors("test@example.com")).thenReturn(Optional.of(user));
        when(vendorRepository.save(any(Vendor.class))).thenAnswer(invocation -> {
            Vendor vendor = invocation.getArgument(0);
            vendor.setId(2L);
            return vendor;
        });
        when(mcRepository.save(any(Mc.class))).thenAnswer(invocation -> {
            Mc mc = invocation.getArgument(0);
            mc.setId(2L);
            return mc;
        });

        // Call the method
        VendorResponse response = vendorRegistrationMethod.vendorRegistration(request2);

        // Verify
        assertNotNull(response);
        assertEquals("Registration successful", response.getMessage());
        verify(userRepository).findByEmailWithVendors("test@example.com");
        verify(vendorRepository).save(any(Vendor.class));
        verify(mcRepository).save(argThat(mc ->
                !mc.isDressCodeIncluded() &&
                        mc.getLanguageOptions().equals("English") &&
                        mc.getPerformanceDuration().equals("3 hours") &&
                        mc.getEventTypeSpecialist().equals("Corporate")
        ));
    }
}