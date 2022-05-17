package no.group.petclinic.kafka;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import no.group.petclinic.entity.Vet;

@Configuration
public class KafkaVetsTemplateConfig {
	
	@Value("${kafka.group.id}")
	private String groupId;

	@Bean
	public ReplyingKafkaTemplate<String, String, List<Vet>> replyingVetsTemplate(
			ProducerFactory<String, String> pf,
			ConcurrentKafkaListenerContainerFactory<String, List<Vet>> factory){
		
		ConcurrentMessageListenerContainer<String, List<Vet>> repliesContainer =
				factory.createContainer(VetTopicConstants.GET_VETS_REPLY);
		repliesContainer.getContainerProperties().setMissingTopicsFatal(false);
		repliesContainer.getContainerProperties().setGroupId(groupId);
		return new ReplyingKafkaTemplate<String, String, List<Vet>>(pf, repliesContainer);
	}
	
	@Bean
	public KafkaTemplate<String, List<Vet>> replyTemplate(ProducerFactory<String,List<Vet>> pf,
			ConcurrentKafkaListenerContainerFactory<String, List<Vet>> factory){
		KafkaTemplate<String, List<Vet>> kafkaTemplate = new KafkaTemplate<String, List<Vet>>(pf);
		factory.getContainerProperties().setMissingTopicsFatal(false);
		factory.setReplyTemplate(kafkaTemplate);
		return kafkaTemplate;
	}

}
