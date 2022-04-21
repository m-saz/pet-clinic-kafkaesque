package no.group.petclinic.temp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Vet;
import no.group.petclinic.repository.VetRepository;

@Component
public class MyRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);
	
	private VetRepository vetRepository;
	
	public MyRunner(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		List<Vet> vets = vetRepository.findAll();
		
		vets.forEach(vet -> {
			logger.info(vet.toString());
		});

	}

}
