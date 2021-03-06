package com.talissonmelo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talissonmelo.model.User;
import com.talissonmelo.model.enums.Roles;
import com.talissonmelo.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping(value = "/registration")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		if (service.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		user.setRoles(Roles.USER);
		return new ResponseEntity<>(service.save(user), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/names")
	public ResponseEntity<?> getNamesOfUsers(@RequestBody List<Long> idList){
		return ResponseEntity.ok(service.findUsers(idList));
	}

	@GetMapping(value = "/login")
	public ResponseEntity<?> getUser(Principal principal) {
		if (principal == null || principal.getName() == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return ResponseEntity.ok(service.findByUsername(principal.getName()));
	}
	
	@GetMapping
	public ResponseEntity<?> test(){
		return ResponseEntity.ok().body("It is working... ... ...");
	}
}
