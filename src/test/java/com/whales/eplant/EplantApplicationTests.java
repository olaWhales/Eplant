package com.whales.eplant;

import com.whales.eplant.data.model.Event;
import com.whales.eplant.data.model.UserPrincipal;
import com.whales.eplant.data.model.Users;
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
	private UserRegistrationMethod userRegistrationMethod;
    @Autowired
    private EventRepository eventRepository;
	@Autowired
	private EventRegistrationMethod eventRegistrationMethod ;


	public UserRegistrationRequest testToRegisterUser() {
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
		return userRegistrationRequest;
	}

	@Test
	void testEventRegistration() {
		// Mock authenticated user
		Users user = new Users();
		user.setId(1L);
		user.setEmail("test@example.com");
		UserPrincipal userPrincipal = new UserPrincipal(user);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, null);
		SecurityContextHolder.getContext().setAuthentication(auth);

		// Create request
		EventRegistrationRequest request = EventRegistrationRequest.builder()
				.name("Spring Planting Workshop")
				.eventType("WORKSHOP")
				.location("Community Farm, Lagos")
				.description("A workshop on sustainable planting")
				.hour(5) // Fixed: Use Integer, not String
				.startTime(LocalDateTime.now())
				.endTime(LocalDateTime.now().plusHours(5))
				.build();

		// Register event
		EventRegistrationResponse response = eventRegistrationMethod.registerEvent(request);

		// Verify
		assertEquals("SUCCESS", response.getStatus());
		Event savedEvent = eventRepository.findById(response.getEventId()).orElseThrow();
		assertEquals("Spring Planting Workshop", savedEvent.getName());
	}
}
