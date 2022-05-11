package no.group.petclinic.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class SSLConfig {
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	private void configureSSL() throws IOException {
		
		String trustStorePath=env.getProperty("server.ssl.trust-store").split(":")[1];
		Resource resource = new ClassPathResource(trustStorePath);
		String trustStoreAbsolutePath = resource.getURL().toString().split(":")[1];
		
		System.setProperty("javax.net.ssl.trustStore", trustStoreAbsolutePath);
		
		String trustStorePassword = env.getProperty("server.ssl.trust-store-password");
		
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
		
	}
}
