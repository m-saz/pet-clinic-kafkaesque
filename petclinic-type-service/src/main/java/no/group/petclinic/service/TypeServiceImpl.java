package no.group.petclinic.service;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Type;
import no.group.petclinic.kafka.TypeTopicConstants;
import no.group.petclinic.repository.TypeRepository;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

	private final TypeRepository typeRepository;
	private final KafkaTemplate<String, List<Type>> kafkaTemplate;
	
	@Override
	@KafkaListener(topics = TypeTopicConstants.TYPES, groupId = "${kafka.group.id}")
	public void getTypes(@Header(KafkaHeaders.CORRELATION_ID) byte[] correlationId) {
		
		List<Type> data = typeRepository.findAll();
		
		Message<List<Type>> message = MessageBuilder
				.withPayload(data)
				.setHeader(KafkaHeaders.TOPIC, TypeTopicConstants.TYPES_REPLY)
				.setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
				.build();
		kafkaTemplate.send(message);
	}

}
