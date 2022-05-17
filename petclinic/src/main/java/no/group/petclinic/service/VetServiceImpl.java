package no.group.petclinic.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Vet;
import no.group.petclinic.kafka.VetTopicConstants;
import no.group.petclinic.repository.VetRepository;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

	private final VetRepository vetRepository;
	private final KafkaTemplate<String, List<Vet>> kafkaTemplate;
	
	@Override
	@KafkaListener(topics = VetTopicConstants.GET_VETS, groupId = "${kafka.group.id}")
	public void getVets(@Header(KafkaHeaders.CORRELATION_ID) byte[] correlationId) {
		
		List<Vet> data = vetRepository.findAll();
		
		Message<List<Vet>> message = MessageBuilder
				.withPayload(data)
				.setHeader(KafkaHeaders.TOPIC, VetTopicConstants.GET_VETS_REPLY)
				.setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
				.build();
		kafkaTemplate.send(message);
	}
	
}
