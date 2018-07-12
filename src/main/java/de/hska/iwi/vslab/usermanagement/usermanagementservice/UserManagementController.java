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
import org.springframework.web.bind.annotation.RequestBody;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@EnableCircuitBreaker
public class UserManagementController {

	@Autowired
	private UserClient userClient;

  /*
	* USER CRUD
	*/

	@HystrixCommand
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User payload) {
		System.out.println(payload.toString());
		return new ResponseEntity<User>(userClient.createUser(payload), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<Iterable<User>> getUsers() {
		return new ResponseEntity<Iterable<User>>(userClient.getUsers(), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long userId) {
		return new ResponseEntity<>(userClient.getUser(userId), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User payload) {
		return new ResponseEntity<>(userClient.updateUser(userId, payload), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
		return new ResponseEntity<>(userClient.deleteUser(userId), HttpStatus.OK);
	}
  /*
	* ROLE CRUD
	*/

	@HystrixCommand
	@RequestMapping(value = "/roles", method = RequestMethod.POST)
	public ResponseEntity<Role> createRole(@RequestBody Role payload) {
		return new ResponseEntity<Role>(userClient.createRole(payload), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Role>> getRoles() {
		return new ResponseEntity<Iterable<Role>>(userClient.getRoles(), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/roles/{roleId}", method = RequestMethod.GET)
	public ResponseEntity<Role> getRole(@PathVariable Long roleId) {
		return new ResponseEntity<>(userClient.getRole(roleId), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/roles/{roleId}", method = RequestMethod.PUT)
	public ResponseEntity<Role> updateRole(@PathVariable Long roleId, @RequestBody Role payload) {
		return new ResponseEntity<>(userClient.updateRole(roleId, payload), HttpStatus.OK);
	}

	@HystrixCommand
	@RequestMapping(value = "/roles/{roleId}", method = RequestMethod.DELETE)
	public ResponseEntity<Role> deleteRole(@PathVariable Long roleId) {
		return new ResponseEntity<>(userClient.deleteRole(roleId), HttpStatus.OK);
	}

	// @HystrixCommand
	// @RequestMapping(value = "/roles", method = RequestMethod.GET)
	// public ResponseEntity<Iterable<Role>> getRoles() {
	// 	return new ResponseEntity<Iterable<Role>>(userClient.getRoles(), HttpStatus.OK);
	// }
}
