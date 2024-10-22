package com.talissonmelo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.talissonmelo.model.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = service.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Usuário : " + username + ", não cadastrado!.");
		}
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}

}
