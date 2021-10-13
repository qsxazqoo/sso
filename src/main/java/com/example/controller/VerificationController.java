package com.example.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.JwtEntity;
import com.example.services.CryptService;
import com.example.services.JwtService;
import com.example.services.MyUserDetailsService;

@Controller
public class VerificationController {

	//建構後被設置用於認證使用者帳號、密碼的安全過濾器上UsernamePasswordauthenticationFilter
	@Autowired
	private AuthenticationManager authenticationManger;
	
	@Autowired
	private MyUserDetailsService myuserDetailsService;

	@Autowired
	private JwtService jwtTokenUtil;
	
	//登入頁面，由SecurityConfigurer設定formlogin
		@RequestMapping("/login")
	    public String showLogin() {
	        return "index.html";
	    }
	
	//登入成功後，由SecurityConfigurer設定defaultSuccessUrl
	@RequestMapping("/homepage")
    public String showHome(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//登入成功後，可取得登入的帳號
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("目前登入帳號：" + username);
		//將用戶資訊設置jwt裡面
//		CryptService crpy=new CryptService();
		String cookies = cookieGetter("jwt",request.getCookies());
		System.out.println("cookies :" + cookies);
		if(cookies==null) {
			String jwt = jwtTokenUtil.generateToken(username);
			Cookie cookie = new Cookie("jwt",jwt);
			cookie.setMaxAge(1*60);
			cookie.setPath("/");
			response.addCookie(cookie);
			//是否過期及token是否正確
		}else if(jwtTokenUtil.isTokenExpired(cookies) && jwtTokenUtil.extractAllClaims(cookies)==null){
			System.out.println("過期");
			return "redirect:http://127.0.0.1:8081/logout";
		}
//		SecurityContextHolder.getContext().setAuthentication(null);
		return "main_john.html";
    }
		
	//暫時用不到
	//取得token
//	@ResponseBody
//	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//	//ResponseEntity用來處理HTTP的返回請求                 @RequestBody Users authenticationRequest
//	public ResponseEntity<?> createAuthenticationToken(@RequestParam("username")String username,@RequestParam("password")String password)
//			throws Exception {
//		try {
//			//驗證帳號密碼
//			Authentication result = authenticationManger.authenticate(new UsernamePasswordAuthenticationToken(
//					username, password));
//			System.out.println(result);
//		} catch (BadCredentialsException e) {
//			throw new Exception("帳號或密碼錯誤", e);
//		}
//		//取得用戶資訊
//		UserDetails userDetails = myuserDetailsService.loadUserByUsername(username);
//		System.out.println(userDetails);
//		//將用戶資訊設置jwt裡面
//		final String jwt = jwtTokenUtil.generateToken(username);
//		CryptService crpy=new CryptService();
//		return ResponseEntity.ok(new JwtEntity(crpy.encrypt(jwt)));
//	}
		
		//重複登入導向
		@RequestMapping("repeat")
		public String loginRepeat() {
			System.out.println("重複");
			return "repeat.html";
		}
		
		//暫時用不到
//		@ResponseBody
//		@GetMapping("/verification")
//		public String verification() {
//			return "homepage";
//		}
//		
		//導回登入頁面
		@ResponseBody
		@GetMapping("/relogin")
		public String relogin() {
			return "login";
		}
		
		//回傳驗證JWT是否正確
		@RequestMapping("/check")
		@ResponseBody
		public String check(@RequestParam("jwt")String jwt) {
//				CryptService cryp = new CryptService();
				jwt.replaceAll("_", "+");
//				String deJwt = cryp.decrypt(jwt);
				System.out.println("jwt  "+jwt);
				if(!jwtTokenUtil.isTokenExpired(jwt) && jwtTokenUtil.extractAllClaims(jwt)!=null) {
				String username = jwtTokenUtil.extractUsername(jwt);
				System.out.println("username  "+username);
				return "success";
				}
			return "failed";
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
