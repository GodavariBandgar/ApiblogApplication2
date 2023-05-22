package com.bikkadit.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	private String username;           //email ch username ahe
	
	private String password;

}
