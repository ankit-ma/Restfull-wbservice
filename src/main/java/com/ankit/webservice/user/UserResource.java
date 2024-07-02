package com.ankit.webservice.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
public class UserResource {
	
	@Autowired
	public UserServiceDao userServiceDao;

	@Autowired
	public UserRepository repository;
	
	@Autowired
	public PostRepository postRepository;
	
	@GetMapping("/users")
	public List<User> retrievAllUser(){
		return repository.findAll();
	}
	
	/*
	 * Adding HATEOAS (Add more info to response which helps for further pi call)
	 * Spring has hatoas dependencies which help to do it in more simple way
	 * 1. first wrap the returning entity into hateosEntity -> EntityModel
	 * 2. Using WebMvcLinkBuilder we build link from method and add it to EntityModel
	 * 
	 * In simple wods here we tell user these are the subsequent api calls you can make 
	 */
	@GetMapping("/users/{id}")
	public EntityModel<User> retrievUserById(@PathVariable Integer id){
//		User user = userServiceDao.findById(id);
		Optional<User> user = repository.findById(id);
	//	System.err.println("User: "+user);
		if(user.isEmpty()) {
			throw new UserNotFoundException("User with id: "+id+ " Not found" );
		}
		
		EntityModel<User> res = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievAllUser());
		res.add(link.withRel("all-users"));
		return res;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
	User savedUser = repository.save(user);
	URI locationUri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId())
			.toUri();
	return ResponseEntity.created(locationUri).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable Integer id) {
		repository.deleteById(id);

	}
	
	@GetMapping("/users/{id}/posts")
	public List<Post> getPostByUser(@PathVariable Integer id){
		Optional<User> userOptional = repository.findById(id);
		
		if(userOptional.isEmpty()) {
			throw new UserNotFoundException("User with id: "+id+" not found");
		}
		return userOptional.get().getPosts();
	}
	
	@PostMapping("/users/{id}/posts")
	public void createPostOfUser(@PathVariable Integer id,@RequestBody Post post) {
		Optional<User> user = repository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("User with id: "+id+" not found");
		}
		post.setUser(user.get());
		postRepository.save(post);
	}
	
	
}
