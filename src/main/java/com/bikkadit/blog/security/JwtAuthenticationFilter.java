package com.bikkadit.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get Token
		
		String requestToken=request.getHeader("Authorization");
		
		//Bearer 2352523sdgsg
		System.out.println(requestToken);
		
		String username=null;
		
		String token=null;
		
		if(requestToken !=null && requestToken.startsWith("Bearer")) {
			
			token= requestToken.substring(7);
			try {
			 username = this.jwtTokenHelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException e) {
				System.out.println("unable to get Jwt token");
			}
			catch(ExpiredJwtException e) {
				System.out.println("Jwt token Expired");
				
			}
			catch(MalformedJwtException e) {
				System.out.println("invalid jwt");
				
			}
		}else {
			System.out.println("jwt token does not begin with Bearer");
		}
		
		//yekbar tokan mila to usko validate karenge
		
		if( username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				
				//authentication banvayache
				
			
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}else {

				System.out.println("Invalidate jwt token");
				
			}
		}
		else {
			
			System.out.println("username is null or context is not null");
			
		}
		
		
		filterChain.doFilter(request, response);
		
	}

}
