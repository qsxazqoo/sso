package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.services.JwtRequestFilterService;
import com.example.services.MyUserDetailsService;

//Security配置
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtRequestFilterService jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 將用戶詳細資料傳入
		auth.userDetailsService(myUserDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//允許使用者直接訪問
			.antMatchers("/repeat", "/js/**", "/css/**","/img/**","/lib/**",
					"/authenticate","/password_john.html","/register.html","/relogin","/check").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			//網址("/")後面隨便打也會導到("/")
			.defaultSuccessUrl("/homepage",true).permitAll()
			.and()
			.csrf().disable()
			.sessionManagement()
			//如果session失效，自動跳轉
			.invalidSessionUrl("/login")
			//重複登入，會踢掉前者
			.maximumSessions(1)
			//重複登入後導向
			.expiredUrl("/repeat");
		
			http.logout()
			//退出刪除cookie
			.deleteCookies("jwt")
			//登出導向
			.logoutSuccessUrl("/login.html");
			
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
