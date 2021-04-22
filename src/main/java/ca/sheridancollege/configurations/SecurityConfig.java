package ca.sheridancollege.configurations;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/**").permitAll();
			//.and()
			//.formLogin()
			//	.loginPage("/")
			//	.defaultSuccessUrl("/account")
			//	.permitAll()
			//;
		
		http.authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/h2-console/**").permitAll();
		
		http.csrf().disable();
        http.headers().frameOptions().disable();
	}
}
