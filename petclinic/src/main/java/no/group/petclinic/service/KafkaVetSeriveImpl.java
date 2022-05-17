package no.group.petclinic.service;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Vet;

@Service
@RequiredArgsConstructor
public class KafkaVetSeriveImpl implements KafkaVetService {

	private final VetService vetService;
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	public List<Vet> getVets() {
		
		kafkaTemplate.send("vets", "Hello, Kafka!");
		
		return vetService.getVets();
	}

}
