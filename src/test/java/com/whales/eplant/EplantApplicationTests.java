package com.whales.eplant;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.McRepository;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.data.repository.VendorRepository;
import com.whales.eplant.dto.request.event.EventRegistrationRequest;
import com.whales.eplant.dto.request.mc.McRequest;
import com.whales.eplant.dto.request.registrationRequest.UserRegistrationRequest;
import com.whales.eplant.dto.response.event.EventRegistrationResponse;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import com.whales.eplant.services.Event.EventRegistrationMethod;
import com.whales.eplant.services.Vendor.VendorRegistrationMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EplantApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EventRegistrationMethod eventRegistrationMethod ;
    @Autowired
    private VendorRepository vendorRepository;
	@Autowired
	private VendorRegistrationMethod vendorRegistrationMethod;
    @Autowired
    private McRepository mcRepository;

	@Test
	public void testToRegisterUser() {
		UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
		Users user = new Users();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmail("john@doe.com");
		user.setPassword("password");
		userRegistrationRequest.setFirstName(user.getFirstName());
		userRegistrationRequest.setLastName(user.getLastName());
		userRegistrationRequest.setEmail(user.getEmail());
		userRegistrationRequest.setPassword(user.getPassword());
		userRepository.save(user);

		assertEquals(userRegistrationRequest.getFirstName(), user.getFirstName());
		assertEquals(userRegistrationRequest.getLastName(), user.getLastName());
	}

	public Users userMethodAuthentication(){
		// Mock authenticated user
		Users user = new Users();
		user.setId(3L);
		user.setEmail("ajaditaoreed100@gmail.com");
		UserPrincipal userPrincipal = new UserPrincipal(user);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return user;
	}

	@Test
	void testEventRegistrationByAlreadyRegisteredUser() {
		// Mock authenticated user
		userMethodAuthentication();
		// Create request
		EventRegistrationRequest request = EventRegistrationRequest.builder()
				.name("Spring Planting Workshop")
				.eventType("WORKSHOP")
				.location("Community Farm, Lagos")
				.description("A workshop on sustainable planting")
				.hour(5)
				.startTime("01:00pm")
				.endTime("06:00pm")
				.build();
//		 Register event
		EventRegistrationResponse response = eventRegistrationMethod.registerEvent(request);
//		 Verify
		assertEquals("Event registered successfully", response.getMessage());
	}

//	@Test
//	public void testToRegisterMcAsAVendor() {
//		// Mock authenticated user
//		Users user = Users.builder()
//				.email("john.doe@example.com")
//				.firstName("John")
//				.lastName("Doe")
//				.password("hashedPassword")
//				.enabled(true)
//				.build();
//		userRepository.save(user);
//
//		// Set up authentication
//		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//				user.getEmail(), null, null
//		);
//		SecurityContextHolder.getContext().setAuthentication(auth);
//
//		// Create McRequest
//		McRequest vendorRequest = McRequest.builder()
//				.price(BigDecimal.valueOf(100000.00))
//				.description("Professional MC for weddings")
//				.bonus(BigDecimal.valueOf(100.00))
//				.availability(true)
//				.role(Role.MC)
//				.dressCodeIncluded(true)
//				.languageOptions("Yoruba")
//				.performanceDuration("5hours")
//				.eventTypeSpecialist("all")
//				.build();
//
//		// Register vendor
//		VendorResponse response = vendorRegistrationMethod.vendorRegistration(vendorRequest);
//
//		// Verify response
//		assertEquals("Registration successful", response.getMessage());
//
//		// Verify vendor in database
//		Vendor savedVendor = vendorRepository.findAll().stream()
//				.filter(v -> v.getUser().getEmail().equals(user.getEmail()))
//				.findFirst()
//				.orElseThrow(() -> new AssertionError("Vendor not found"));
//		assertEquals(Role.MC, savedVendor.getRole());
//		assertEquals(BigDecimal.valueOf(100000.00), savedVendor.getPrice());
//		assertEquals("Yoruba", savedVendor.getRoleAttributes().contains("Yoruba"));
//	}


}
