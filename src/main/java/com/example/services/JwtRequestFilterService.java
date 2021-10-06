package com.example.services;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class JwtRequestFilterService extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtService jwtUtil;

	// 驗證token是否正確
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// 取得request Header的authorization
		String authorizationHeader = cookieGetter("jwt", request.getCookies());
//		String authorization = null;
//		if (authorizationHeader != null) {
//			try {
//				CryptService cryp = new CryptService();
//				authorization = authorizationHeader;
//			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		String username = null;
		String jwt = null;
		// 檢查不為空
		if (authorizationHeader != null) {
			jwt = authorizationHeader;
			// 從token取得username
			username = jwtUtil.extractUsername(jwt);
		} 
//		else
//			System.out.println("authorization    " + authorizationHeader);
		// 用戶名不為空
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// 取得用戶資訊
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			// 檢查token是否有過期和用戶資訊是否一致
			if (jwtUtil.isTokenExpired(jwt)) {
				// 通過驗證後裝載使用者的完整資訊(ex.username(使用者名稱).password(密碼).authorities(權限)等.)
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}

		}
		// 允許任何網域存取資源
		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, content-type");
		if (request.getMethod().equals(HttpMethod.OPTIONS.name()) && (authorizationHeader == null)) {
				response.setStatus(HttpStatus.NO_CONTENT.value());
		} else {
			chain.doFilter(request, response);
		}
	}

	private String cookieGetter(String key, Cookie[] cookies) {
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(key)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}
}
