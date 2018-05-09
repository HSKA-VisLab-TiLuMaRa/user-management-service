package de.hska.iwi.vslab.usermanagement.usermanagementservice;

// import de.hska.iwi.vslab.user.userservice.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;



@Component
public class UserClient {

	private final Map<Long, User> userCache = new LinkedHashMap<Long, User>();
	private final Map<Long, Role> roleCache = new LinkedHashMap<Long, Role>();

	@Autowired
	private RestTemplate restTemplate;

	/*
	* USER CRUD CORE SERVICE
	*/

	@HystrixCommand(fallbackMethod = "createUserFallback", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public User createUser(User payload) {
		User tmpuser = restTemplate.postForObject("http://user-service/users", payload, User.class);
		return tmpuser;
	}

	@HystrixCommand(fallbackMethod = "getUsersCache", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public Iterable<User> getUsers() {
		Collection<User> users = new HashSet<User>();
		User[] tmpusers = restTemplate.getForObject("http://user-service/users", User[].class);
		Collections.addAll(users, tmpusers);
		userCache.clear();
		users.forEach(u -> userCache.put(u.getId(), u));
		return users;
	}

	@HystrixCommand(fallbackMethod = "getUserCache", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public User getUser(Long userId) {
		User tmpuser = restTemplate.getForObject("http://user-service/users/" + userId, User.class);
		userCache.putIfAbsent(userId, tmpuser);
		return tmpuser;
	}

	@HystrixCommand(fallbackMethod = "updateUserFallback", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public User updateUser(Long userId, User payload) {
		restTemplate.put("http://user-service/users/" + userId, payload);
		return new User();
	}

	@HystrixCommand(fallbackMethod = "deleteUserFallback", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public User deleteUser(Long userId) {
		restTemplate.delete("http://user-service/users/" + userId);
		return new User();
	}

	/*
	* ROLE CRUD CORE SERVICE
	*/

	@HystrixCommand(fallbackMethod = "createRoleFallback", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public Role createRole(Role payload) {
		Role tmprole = restTemplate.postForObject("http://user-service/roles", payload, Role.class);
		return tmprole;
	}

	@HystrixCommand(fallbackMethod = "getRolesCache", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public Iterable<Role> getRoles() {
		Collection<Role> roles = new HashSet<Role>();
		Role[] tmproles = restTemplate.getForObject("http://user-service/roles", Role[].class);
		Collections.addAll(roles, tmproles);
		roleCache.clear();
		roles.forEach(r -> roleCache.put(r.getId(), r));
		return roles;
	}

	@HystrixCommand(fallbackMethod = "getRoleCache", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public Role getRole(Long roleId) {
		Role tmprole = restTemplate.getForObject("http://user-service/roles/" + roleId, Role.class);
		roleCache.putIfAbsent(roleId, tmprole);
		return tmprole;
	}

	@HystrixCommand(fallbackMethod = "updateRoleFallback", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public Role updateRole(Long roleId, Role payload) {
		restTemplate.put("http://user-service/roles/" + roleId, payload);
		return new Role();
	}

	@HystrixCommand(fallbackMethod = "deleteRoleFallback", commandProperties = {
	@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
	public Role deleteRole(Long roleId) {
		restTemplate.delete("http://user-service/roles/" + roleId);
		return new Role();
	}

	/*
	* USER CRUD FALLBACK
	*/

	public User createUserFallback(User payload){
		return payload;
	}

	public Iterable<User> getUsersCache() {
		return userCache.values();
	}

	public User getUserCache(Long userId) {
		return userCache.getOrDefault(userId, new User());
	}

	public User updateUserFallback(Long userId, User payload){
		return payload;
	}

	public User deleteUserFallback(Long userId){
		return new User();
	}
	/*
	* ROLE CRUD FALLBACK
	*/

	public Role createRoleFallback(Role payload){
		return payload;
	}

	public Iterable<Role> getRolesCache() {
		return roleCache.values();
	}

	public Role getRoleCache(Long getRoleId) {
		return roleCache.getOrDefault(getRoleId, new Role());
	}

	public Role updateRoleFallback(Long getRoleId, Role payload){
		return payload;
	}

	public Role deleteRoleFallback(Long getRoleId){
		return new Role();
	}

	/*
	* BEANS
	*/

	@LoadBalanced
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
     // Do any additional configuration here
     return builder.build();
  }


}
