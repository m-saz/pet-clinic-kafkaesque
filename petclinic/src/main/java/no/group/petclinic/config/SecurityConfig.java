package no.group.petclinic.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
				.antMatchers("/**")
					.access("hasIpAddress('127.0.0.1') or hasIpAddress('::1') or isAuthenticated()")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.permitAll()
				.and()
			.cors()
				.and()
			.csrf().disable();		
	}

	
}
