package com.talissonmelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.talissonmelo.model.User;
import com.talissonmelo.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Override
	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repository.save(user);
	}
	
	@Override
	public User findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}
	
	@Override
	public List<String> findUsers(List<Long> usersId){
		return repository.findByIdList(usersId);
	}
}
