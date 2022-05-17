package no.group.petclinic.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Bean
	public NewTopic topic1() {
		return TopicBuilder.name(VetTopicConstants.GET_VETS).build();
	}
	
	@Bean
	public NewTopic topic2() {
		return TopicBuilder.name(VetTopicConstants.GET_VETS_REPLY).build();
	}
	
	@Bean
	public NewTopic topic3() {
		return TopicBuilder.name(TypeTopicConstants.GET_TYPES).build();
	}
	
	@Bean
	public NewTopic topic4() {
		return TopicBuilder.name(TypeTopicConstants.GET_TYPES_REPLY).build();
	}

}
