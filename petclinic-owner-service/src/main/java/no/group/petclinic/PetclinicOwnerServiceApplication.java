package no.group.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class PetclinicOwnerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetclinicOwnerServiceApplication.class, args);
	}
	

}
