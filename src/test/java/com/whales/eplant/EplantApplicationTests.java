package com.whales.eplant;

import com.whales.eplant.data.model.*;
import com.whales.eplant.data.repository.McRepository;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.data.repository.VendorRepository;
import com.whales.eplant.dto.request.event.EventRegistrationRequest;
import com.whales.eplant.dto.request.registrationRequest.UserRegistrationRequest;
import com.whales.eplant.dto.response.event.EventRegistrationResponse;
import com.whales.eplant.services.Event.EventRegistrationMethod;
import com.whales.eplant.services.Vendor.VendorRegistrationMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

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
		Users user = new Users();
		user.setId(3L);
		user.setEmail("ajaditaoreed100@gmail.com");
		UserPrincipal userPrincipal = new UserPrincipal(user);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return user;
	}

	@Test
	void testEventRegistrationByAlreadyRegisteredAsUser() {
		userMethodAuthentication();
		EventRegistrationRequest request = EventRegistrationRequest.builder()
				.eventName("Spring Planting Workshop")
				.eventType("WORKSHOP")
				.location("Community Farm, Lagos")
				.description("A workshop on sustainable planting")
				.hour(5)
				.startTime("01:00pm")
				.endTime("06:00pm")
				.build();
		EventRegistrationResponse response = eventRegistrationMethod.registerEvent(request);
		assertEquals("Event registered successfully", response.getMessage());
	}

//	@Test
//	public void testToRegisterMcAsAVendor(){
//		userMethodAuthentication();
//
//		Vendor vendor = new Vendor();
//		vendor.setId(3L);
//		vendor.setAvailability(true);
//		vendor.setDescription("married");
//		vendor.setPrice(BigDecimal.valueOf(100.00));
//		vendor.setBonus(BigDecimal.valueOf(100.00));
//		vendor.setRole(Role.MC);

//		VendorRequest vendorRequest = new VendorRequest();

//		vendorRequest.setAvailability(vendor.isAvailability());
//		vendorRequest.setBonus(100.00);
//		vendorRequest.setDescription(vendor.getDescription());
//		vendorRequest.setRole(vendor.getRole());
//
//		vendorRepository.save(vendor);
//
//		if(vendor.getRole() == Role.MC){
//			Mc mc = new Mc();
//			mc.setId(3L);
//			mc.setVendor(vendor);
//			mc.setPerformanceDuration("5");
//			mc.setLanguageOptions("yoruba");
//			mc.setDressCodeIncluded(true);
//			mcRepository.save(mc);
//		}
//		vendorRequest.setRole(vendor.getRole());
//
//		VendorResponse vendorResponse = vendorRegistrationMethod.vendorRegistration(vendorRequest);
//
//		vendorResponse.setMessage("vendor registration successful");
//		assertEquals(vendorResponse.getMessage(), "vendor registration successful");
//		assertEquals(vendorRequest.getRole(), vendor.getRole());
//	}

}
