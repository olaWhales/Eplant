package com.whales.eplant;

import com.whales.eplant.data.model.Event;
import com.whales.eplant.data.model.UserPrincipal;
import com.whales.eplant.data.model.Users;
import com.whales.eplant.data.model.Vendor;
import com.whales.eplant.data.repository.EventRepository;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.dto.request.event.EventRegistrationRequest;
import com.whales.eplant.dto.request.registrationRequest.UserRegistrationRequest;
import com.whales.eplant.dto.response.event.EventRegistrationResponse;
import com.whales.eplant.dto.response.registrationResponse.UserRegistrationResponse;
import com.whales.eplant.services.Event.EventRegistrationMethod;
import com.whales.eplant.services.Users.UserRegistrationMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EplantApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EventRegistrationMethod eventRegistrationMethod ;

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

	public void userMethodAuthentication(){
		// Mock authenticated user
		Users user = new Users();
		user.setId(3L);
		user.setEmail("ajaditaoreed100@gmail.com");
		UserPrincipal userPrincipal = new UserPrincipal(user);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
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

	@Test
	public void testToRegisterMcAsAVendor(){
		userMethodAuthentication();

		Vendor vendor = new Vendor();
	}


}
