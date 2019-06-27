package com.example.demoauth.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	public String list() {
		return "{\"users\":[{\"name\":\"Lucas\", \"country\":\"Brazil\"}," +
		           "{\"name\":\"Jackie\",\"country\":\"China\"}]}"; 
	}
}
