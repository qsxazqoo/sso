package com.example.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javassist.expr.NewArray;

@Service
public class JwtService {

	//密鑰
	private String SECRET_KEY="secret";
	
	//取出username
	public String extractUsername(String token) {
		if(extractClaim(token,Claims::getSubject)!=null) {
			return extractClaim(token,Claims::getSubject);	
		}else {
		return null;	
		}
	}
	
	//取出token時效
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	//取的token中的資訊
	public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		if(claims==null) {
			return null;
		}
		return claimsResolver.apply(claims);
	}
	//解析token,回傳payload   claims.get("role").toString(); role=key
	public Claims extractAllClaims(String token) {
		Claims claims = null;
		try {
			claims=Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		} catch (Exception e) {
		return claims;
		}
		return claims;	
	}
	//檢查是否過期(true=過期)
	public Boolean isTokenExpired(String token) {
		System.out.println(extractExpiration(token));
		System.out.println(new Date());
		if(extractExpiration(token)==null) {
			return true;
		}
		return extractExpiration(token).before(new Date());
	}
	//創建token
	public String generateToken(String username) {
		Map<String,Object> claims=new HashMap<>();
		return createToken(claims,username);
	}
	
	/*
	 * setSubject = username
	 * setIssuedAt = 設定token獲取時間
	 * setExpiration = 設定token存活時間
	 */
	//生成token
	private String createToken(Map<String,Object> claims,String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*20))
				.signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
	}
	//驗證帳號是否一致，token是否有過期
	public Boolean validateToken(String token,String username) {
		String name = extractUsername(token);
		return (name.equals(username)&&!isTokenExpired(token));
	}
}
