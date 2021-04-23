package ca.sheridancollege.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private LoginAccessDeniedHandler accessDeniedHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		http
			.authorizeRequests()
				.antMatchers("/possibleConversations", "/possibleConversations/**").hasRole("ARTIST")
				.antMatchers("/conversationAdd", "/conversationAdd/**").hasRole("ARTIST")
				.antMatchers("/account", "/account/**", "/message").hasAnyRole("ARTIST","CUSTOMER")
				.antMatchers("/", "/css/**", "/images/**", "/js/**", "/**").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/account")
				.permitAll()
			.and()
			.logout()
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/?logout")
				.permitAll()
			.and()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
