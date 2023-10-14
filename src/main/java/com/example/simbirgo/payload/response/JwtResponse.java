package com.example.simbirgo.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;

	public JwtResponse(String token, Long id, String username, List<String> roles) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}
	private List<String> roles;
}
