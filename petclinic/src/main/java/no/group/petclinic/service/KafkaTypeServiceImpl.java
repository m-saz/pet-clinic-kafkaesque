package no.group.petclinic.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.Type;
import no.group.petclinic.exception.TypesNotFoundException;
import no.group.petclinic.kafka.TypeTopicConstants;

@Service
@RequiredArgsConstructor
public class KafkaTypeServiceImpl implements KafkaTypeService {

	private final ReplyingKafkaTemplate<String, String, List<Type>> replyingKafkaTemplate;
	
	@Override
	public List<Type> getTypes(){
		ProducerRecord<String, String> record = 
				new ProducerRecord<>(TypeTopicConstants.TYPES, null);
		RequestReplyFuture<String, String, List<Type>> future =
				replyingKafkaTemplate.sendAndReceive(record);
		ConsumerRecord<String, List<Type>> response = null;
		try {
			response = future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new TypesNotFoundException("Unable to find types");
		}
		
		return response.value();
	}

}
