package com.talissonmelo.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.talissonmelo.model.User;

@Component
public interface UserService {

	public User save(User user);
	public User findByUsername(String username);
	public List<String> findUsers(List<Long> usersId);
}
