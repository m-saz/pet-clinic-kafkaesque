package no.group.petclinic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Value("${allowed.origins}")
	private String[] allowedOrigins;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		String[] allowedMethods = { "POST", "GET", "PUT", "DELETE" };
		registry.addMapping("/api/**")
					.allowedOrigins(allowedOrigins)
					.allowedMethods(allowedMethods);
		
	}

	
}
