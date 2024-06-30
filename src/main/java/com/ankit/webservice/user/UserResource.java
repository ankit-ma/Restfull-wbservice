package com.ankit.webservice.user;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	
	@Autowired
	public UserServiceDao userServiceDao;

	
	@GetMapping("/users")
	public List<User> retrievAllUser(){
		return userServiceDao.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User retrievUserById(@PathVariable Integer id){
		User user = userServiceDao.findById(id);
		if(user== null) {
			throw new UserNotFoundException("User with id: "+id+ " Not found" );
		}
		return user;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
	User savedUser = userServiceDao.save(user);
	URI locationUri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
	return ResponseEntity.created(locationUri).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable Integer id) {
		userServiceDao.deleteById(id);

	}
	
}
