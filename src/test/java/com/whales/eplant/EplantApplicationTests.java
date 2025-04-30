package com.whales.eplant;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.McRepository;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.data.repository.VendorRepository;
import com.whales.eplant.dto.request.event.EventRegistrationRequest;
import com.whales.eplant.dto.request.mc.McRequest;
import com.whales.eplant.dto.request.registrationRequest.UserRegistrationRequest;
import com.whales.eplant.dto.request.vendor.VendorRequest;
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
//	public void testToRegisterMcAsAVendor(){
//		userMethodAuthentication();
//
//		Vendor vendor = new Vendor();
//		VendorRequest request = new VendorRequest();
//		vendor.setId(3L);
//		vendor.setDescription("i am dj");
//		vendor.setBonus(BigDecimal.TEN);
//		vendor.setPrice(BigDecimal.TEN);
//		vendor.setRole(Role.MC);
//
//		request.setBonus(BigDecimal.TEN);
//		request.setPrice(BigDecimal.TEN);
//		request.setDescription(vendor.getDescription());
//		request.setRole(Role.MC);
//		request.setDescription(vendor.getDescription());
//		vendorRepository.save(vendor);
//
//		Mc mc = new Mc();
//		McRequest mcRequest = new McRequest();
//		mc.setId(3L);
//		mc.setEventTypeSpecialist("married");
//		mc.setLanguageOptions("yoruba");
//		mc.setDressCodeIncluded(true);
//		mc.setPerformanceDuration("5");

//		mcRequest.setEventTypeSpecialist(request.getRole().getMc.getEventTypeSpecialist());
//		mcRequest.setLanguageOptions(request.getRole().getMc.getLanguageOptions());
//		mcRequest.setPerformanceDuration(request.getRole().getMc.getPerformanceDuration());
//		mcRequest.setLanguageOptions(request.getRole().getMc.getLanguageOptions());
//		mcRepository.save(mc);
//
////		VendorResponse response = vendorRegistrationMethod.vendorRegistration(request);
//
//		response.setMessage("One vendor (Mc) created successfully ");
//		assertEquals(response.getMessage(), "One vendor (Mc) created successfully ");
//	}

}
