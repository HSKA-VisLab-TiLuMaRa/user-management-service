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

	public Iterable<Role> getRolesCache() {
		return roleCache.values();
	}

	public Iterable<User> getUsersCache() {
		return userCache.values();
	}

	public User getUserCache(Long userId) {
		return userCache.getOrDefault(userId, new User());
	}

	@LoadBalanced
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
     // Do any additional configuration here
     return builder.build();
  }


}
