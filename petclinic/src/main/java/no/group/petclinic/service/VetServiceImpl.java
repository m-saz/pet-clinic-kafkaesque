package no.group.petclinic.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Vet;
import no.group.petclinic.repository.VetRepository;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

	private final VetRepository vetRepository;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<Vet> getVets() {
		return vetRepository.findAll();
	}
	
	@KafkaListener(topics = "vets")
	public void listen(String data) {
		LOG.info("We recieved cryptic message: {}", data);
	}

}
