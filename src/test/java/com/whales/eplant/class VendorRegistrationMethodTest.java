package com.whales.eplant;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.*;
import com.whales.eplant.dto.request.dj.DjAttributes;
import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import com.whales.eplant.services.Vendor.VendorRegistrationMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
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
    private DjRepository djRepository;

    @InjectMocks
    private VendorRegistrationMethod vendorRegistrationMethod;

    private Users user;
    private VendorRequest vendorRequest;

    @BeforeEach
    void setUp() {
        user = Users.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        vendorRequest = VendorRequest.builder()
                .price(200.00)
                .description("Awesome DJ Service")
                .bonus(500.00)
                .availability(true)
                .role(Role.DJ)
                .djAttributes(DjAttributes.builder()
                        .musicGenres(Collections.singletonList("Afro beats"))
                        .numberOfSpeakers(2)
                        .numberOfMicrophones(2)
                        .performanceDuration("2 hours")
                        .lightingIncluded(true)
                        .build())
                .build();
    }

    @Test
    void testVendorRegistration_SuccessfulDjRegistration() {
        // Mock security context
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findByEmailWithVendors(user.getEmail()))
                .thenReturn(Optional.of(user));

        VendorResponse response = vendorRegistrationMethod.vendorRegistration(vendorRequest);

        assertNotNull(response);
        assertEquals("Registration successful", response.getMessage());

        verify(userRepository, times(1)).findByEmailWithVendors(user.getEmail());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
        verify(djRepository, times(1)).save(any(Dj.class));
    }

    @Test
    void testVendorRegistration_UserNotAuthenticated_ShouldThrowException() {
        SecurityContextHolder.clearContext(); // No auth set

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                vendorRegistrationMethod.vendorRegistration(vendorRequest)
        );

        assertEquals("User not authenticated", exception.getMessage());
        verifyNoInteractions(userRepository);
        verifyNoInteractions(vendorRepository);
    }

    @Test
    void testVendorRegistration_UserNotFound_ShouldThrowException() {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findByEmailWithVendors(user.getEmail()))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                vendorRegistrationMethod.vendorRegistration(vendorRequest)
        );

        assertTrue(exception.getMessage().contains("User not found"));
        verify(userRepository, times(1)).findByEmailWithVendors(user.getEmail());
    }

    @Test
    void testVendorRegistration_NoRoleProvided_ShouldThrowException() {
        vendorRequest.setRole(null);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findByEmailWithVendors(user.getEmail()))
                .thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                vendorRegistrationMethod.vendorRegistration(vendorRequest)
        );

        assertEquals("Role is required", exception.getMessage());
    }
}
