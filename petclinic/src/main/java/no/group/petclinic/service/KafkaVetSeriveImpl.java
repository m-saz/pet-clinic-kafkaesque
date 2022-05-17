package no.group.petclinic.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.Vet;
import no.group.petclinic.exception.VetsNotFoundException;
import no.group.petclinic.kafka.VetTopicConstants;

@Service
@RequiredArgsConstructor
public class KafkaVetSeriveImpl implements KafkaVetService {

	private final ReplyingKafkaTemplate<String, String, List<Vet>> replyingKafkaTemplate;
	
	@Override
	public List<Vet> getVets(){
		
		ProducerRecord<String, String> record = 
				new ProducerRecord<String, String>(VetTopicConstants.GET_VETS, null);
		RequestReplyFuture<String, String, List<Vet>> future =
				replyingKafkaTemplate.sendAndReceive(record);
		ConsumerRecord<String, List<Vet>> response;
		try {
			response = future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new VetsNotFoundException("Unable to find vets");
		}
		
		return response.value();
	}

}
