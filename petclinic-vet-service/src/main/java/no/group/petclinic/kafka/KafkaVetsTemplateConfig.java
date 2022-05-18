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

	@Bean
	public KafkaTemplate<String, List<Vet>> vetsTemplate(ProducerFactory<String,List<Vet>> pf,
			ConcurrentKafkaListenerContainerFactory<String, List<Vet>> factory){
		KafkaTemplate<String, List<Vet>> kafkaTemplate = new KafkaTemplate<>(pf);
		factory.getContainerProperties().setMissingTopicsFatal(false);
		return kafkaTemplate;
	}

}
