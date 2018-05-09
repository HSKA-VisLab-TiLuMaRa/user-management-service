package de.hska.iwi.vslab.usermanagement.usermanagementservice;

// import de.hska.iwi.vslab.user.userservice.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@EnableCircuitBreaker
public class UserManagementController {

	@Autowired
	private UserClient userClient;

	@HystrixCommand
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<Iterable<User>> getUsers() {
		return new ResponseEntity<Iterable<User>>(userClient.getUsers(), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Role>> getRoles() {
		return new ResponseEntity<Iterable<Role>>(userClient.getRoles(), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long userId) {
		return new ResponseEntity<>(userClient.getUser(userId), HttpStatus.OK);
	}

}
