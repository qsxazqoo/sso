//package com.example.services;
//
//import org.springframework.stereotype.Service;
//
//import com.example.apiconnector.TokenApiConnector;
//
//@Service
//public class JwtServiceApi {
//
//	TokenApiConnector api= new TokenApiConnector();
//	
//	public String sendToken(String username) {
//		return api.sendToken(username);
//	}
//	
//	public String extractUsername(String jwt) {
//		return api.extractUsername(jwt);
//	}
//	
//	public String validateToken(String jwt,String username) {
//		return api.validateToken(jwt,username);
//	}
//}
