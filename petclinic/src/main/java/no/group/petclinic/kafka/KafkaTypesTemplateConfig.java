package no.group.petclinic.kafka;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import no.group.petclinic.dto.Type;

@Configuration
public class KafkaTypesTemplateConfig {

	@Value("${kafka.group.id}")
	private String groupId;

	@Bean
	public ReplyingKafkaTemplate<String, String, List<Type>> typesReplyingTemplate(
			ProducerFactory<String, String> pf,
			ConcurrentKafkaListenerContainerFactory<String, List<Type>> factory){
		
		ConcurrentMessageListenerContainer<String, List<Type>> repliesContainer =
				factory.createContainer(TypeTopicConstants.TYPES_REPLY);
		repliesContainer.getContainerProperties().setMissingTopicsFatal(false);
		repliesContainer.getContainerProperties().setGroupId(groupId);
		return new ReplyingKafkaTemplate<String, String, List<Type>>(pf, repliesContainer);
	}
}
