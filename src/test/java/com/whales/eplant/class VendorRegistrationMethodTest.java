package com.whales.eplant;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.*;
import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import com.whales.eplant.services.Vendor.VendorRegistrationMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.*;

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

    @Mock
    private DjRepository djRepository;

    @InjectMocks
    private VendorRegistrationMethod vendorRegistrationMethod;

    private Users user;
    private VendorRequest vendorRequest;

    @BeforeEach
    void setUp() {
        // Set up authenticated user
        user = new Users();
        user.setEmail("test@example.com");
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("test@example.com", null, null)
        );

        // Set up default VendorRequest
        vendorRequest = new VendorRequest();
        vendorRequest.setPrice(Double.valueOf("100.00"));
        vendorRequest.setDescription("DJ Services");
        vendorRequest.setBonus(Double.valueOf("10.00"));
        vendorRequest.setAvailability(true);
        vendorRequest.setRole(Role.DJ);

        Map<String, Object> roleAttributes = new HashMap<>();
        roleAttributes.put("lightingIncluded", true);
        roleAttributes.put("numberOfSpeakers", 4);
        roleAttributes.put("performanceDuration", "4 hours");
        roleAttributes.put("numberOfMicrophones", 2);
        roleAttributes.put("musicGenres", Arrays.asList("Rock", "Jazz"));
        vendorRequest.setRoleAttributes(roleAttributes);
    }

    @Test
    void testSuccessfulDjRegistration() {
        // Arrange
        when(userRepository.findByEmailWithVendors("test@example.com")).thenReturn(Optional.of(user));
        Vendor savedVendor = Vendor.builder()
                .id(1L)
                .price(new BigDecimal("100.00"))
                .description("DJ Services")
                .bonus(new BigDecimal("10.00"))
                .availability(true)
                .role(Role.DJ)
                .user(user)
                .build();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        when(djRepository.save(any(Dj.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        VendorResponse response = vendorRegistrationMethod.vendorRegistration(vendorRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Registration successful", response.getMessage());
        verify(userRepository).findByEmailWithVendors("test@example.com");
//        verify(vendorRepository).save(any(Vendor.class));
//        verify(djRepository).save(any(Dj.class));
        verifyNoInteractions(mcRepository);
    }

    @Test
    void testSuccessfulMcRegistration() {
        // Arrange
        vendorRequest.setRole(Role.MC);
        Map<String, Object> roleAttributes = new HashMap<>();
        roleAttributes.put("dressCodeIncluded", true);
        roleAttributes.put("languageOptions", "English, Spanish");
        roleAttributes.put("performanceDuration", "3 hours");
        roleAttributes.put("eventTypeSpecialist", "Weddings");
        vendorRequest.setRoleAttributes(roleAttributes);

        when(userRepository.findByEmailWithVendors("test@example.com")).thenReturn(Optional.of(user));
        Vendor savedVendor = Vendor.builder()
                .id(1L)
                .price(new BigDecimal("100.00"))
                .description("DJ Services")
                .bonus(new BigDecimal("10.00"))
                .availability(true)
                .role(Role.MC)
                .user(user)
                .build();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        when(mcRepository.save(any(Mc.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        VendorResponse response = vendorRegistrationMethod.vendorRegistration(vendorRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Registration successful", response.getMessage());
        verify(userRepository).findByEmailWithVendors("test@example.com");
        verify(vendorRepository).save(any(Vendor.class));
        verify(mcRepository).save(any(Mc.class));
        verifyNoInteractions(djRepository);
    }

    @Test
    void testOptimisticLockingFailure() {
        // Arrange
        when(userRepository.findByEmailWithVendors("test@example.com")).thenReturn(Optional.of(user));
        when(vendorRepository.save(any(Vendor.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Vendor", 3L));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            vendorRegistrationMethod.vendorRegistration(vendorRequest);
        });
        assertEquals("Vendor was modified by another transaction. Please try again.", exception.getMessage());
        verify(userRepository).findByEmailWithVendors("test@example.com");
        verify(vendorRepository).save(any(Vendor.class));
        verifyNoInteractions(djRepository, mcRepository);
    }

    @Test
    void testUserNotFound() {
        // Arrange
        when(userRepository.findByEmailWithVendors("test@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            vendorRegistrationMethod.vendorRegistration(vendorRequest);
        });
        assertEquals("User not found: test@example.com", exception.getMessage());
        verify(userRepository).findByEmailWithVendors("test@example.com");
        verifyNoInteractions(vendorRepository, djRepository, mcRepository);
    }

    @Test
    void testNullRole() {
        // Arrange
        vendorRequest.setRole(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendorRegistrationMethod.vendorRegistration(vendorRequest);
        });
        assertEquals("Role is required", exception.getMessage());
        verifyNoInteractions(userRepository, vendorRepository, djRepository, mcRepository);
    }

    @Test
    void testEmptyRoleAttributes() {
        // Arrange
        vendorRequest.setRoleAttributes(new HashMap<>());
        when(userRepository.findByEmailWithVendors("test@example.com")).thenReturn(Optional.of(user));
        Vendor savedVendor = Vendor.builder()
                .id(1L)
                .price(new BigDecimal("100.00"))
                .description("DJ Services")
                .bonus(new BigDecimal("10.00"))
                .availability(true)
                .role(Role.DJ)
                .user(user)
                .build();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
        when(djRepository.save(any(Dj.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        VendorResponse response = vendorRegistrationMethod.vendorRegistration(vendorRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Registration successful", response.getMessage());
        verify(userRepository).findByEmailWithVendors("test@example.com");
        verify(vendorRepository).save(any(Vendor.class));
        verify(djRepository).save(any(Dj.class));
    }

    @Test
    void testInvalidMusicGenresType() {
        // Arrange
        Map<String, Object> roleAttributes = new HashMap<>();
        roleAttributes.put("lightingIncluded", true);
        roleAttributes.put("numberOfSpeakers", 4);
        roleAttributes.put("performanceDuration", "4 hours");
        roleAttributes.put("numberOfMicrophones", 2);
        roleAttributes.put("musicGenres", Arrays.asList(123, "Jazz")); // Invalid type
        vendorRequest.setRoleAttributes(roleAttributes);

        when(userRepository.findByEmailWithVendors("test@example.com")).thenReturn(Optional.of(user));
        Vendor savedVendor = Vendor.builder()
                .id(1L)
                .price(new BigDecimal("100.00"))
                .description("DJ Services")
                .bonus(new BigDecimal("10.00"))
                .availability(true)
                .role(Role.DJ)
                .user(user)
                .build();
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendorRegistrationMethod.vendorRegistration(vendorRequest);
        });
        assertTrue(exception.getMessage().contains("Non-String element found"));
        verify(userRepository).findByEmailWithVendors("test@example.com");
        verify(vendorRepository).save(any(Vendor.class));
        verifyNoInteractions(djRepository);
    }
}