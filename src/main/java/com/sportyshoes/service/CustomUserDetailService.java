package com.sportyshoes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sportyshoes.model.CustomUserDetails;
import com.sportyshoes.model.User;
import com.sportyshoes.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.getUserByEmail(email);
				if(user==null) {
					throw new UsernameNotFoundException("Could not found user");
				}
				CustomUserDetails customUser = new CustomUserDetails(user);
				return customUser;
	}

	public List<User> searchUsers(String keyword) {
		if (keyword != null) {
			return userRepository.search(keyword);
		}
		return (List<User>) userRepository.findAll();
	}

	public Optional<User> getUserById(int id) {
		return userRepository.findById(id);
	}
}
