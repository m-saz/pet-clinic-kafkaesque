package no.group.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class PetclinicVetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetclinicVetServiceApplication.class, args);
	}
	

}
