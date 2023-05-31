package com.example.security.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.security.entities.UserEntities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	private final String key="5368566D597133743677397A244226452948404D635166546A576E5A72347537";
	
	public String extractUsername(String token) {
	return extractClaim(token,Claims::getSubject);	
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	
		private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		// TODO Auto-generated method stub
			final Claims claim=extractAllClaims(token);			
		return claimsResolver.apply(claim); 
	}


		private Claims extractAllClaims(String token) {
			// TODO Auto-generated method stub
			return Jwts
					.parserBuilder()
					.setSigningKey(getSignKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
		}
		
		private Boolean isTokenExpired(String token) {
			return extractExpiration(token).before(new Date());
		}
		
		
		public Boolean validToken(String token,UserDetails userDetails) {
			final String username=extractUsername(token);
			return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
		}

		public String generateToken(String username) {
			Map<String ,Object> claims=new HashMap<>();
			return createToken(claims,username);
		}

		private String createToken(Map<String, Object> claims, String username) {
			// TODO Auto-generated method stub
			
			return Jwts.builder()
					.setClaims(claims)
					.setSubject(username)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
					.signWith(getSignKey(),SignatureAlgorithm.HS256)
					.compact();
		}

		private Key getSignKey() {
			// TODO Auto-generated method stub
			
			byte[] keybyte=Decoders.BASE64.decode(key);
			return Keys.hmacShaKeyFor(keybyte);
		}
}
