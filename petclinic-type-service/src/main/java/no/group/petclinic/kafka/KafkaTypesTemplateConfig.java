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

import no.group.petclinic.entity.Type;

@Configuration
public class KafkaTypesTemplateConfig {

	@Bean
	public KafkaTemplate<String, List<Type>> typesTemplate(ProducerFactory<String,List<Type>> pf,
			ConcurrentKafkaListenerContainerFactory<String, List<Type>> factory){
		KafkaTemplate<String, List<Type>> kafkaTemplate = new KafkaTemplate<>(pf);
		factory.getContainerProperties().setMissingTopicsFatal(false);
		return kafkaTemplate;
	}
}
