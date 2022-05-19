package no.group.petclinic.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import no.group.petclinic.dto.OperationStatus;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;

@Configuration
public class KafkaOwnersTemplateConfig {

	@Bean
	public KafkaTemplate<String, Page<OwnerSlim>> getOwnersReplyTemplate(
			ProducerFactory<String,Page<OwnerSlim>> pf,
			ConcurrentKafkaListenerContainerFactory<String, Page<OwnerSlim>> factory){
		KafkaTemplate<String, Page<OwnerSlim>> kafkaTemplate = new KafkaTemplate<>(pf);
		factory.getContainerProperties().setMissingTopicsFatal(false);
		return kafkaTemplate;
	}
	
	@Bean
	public KafkaTemplate<String, OperationStatus> saveOwnerReplyTemplate(
			ProducerFactory<String,OperationStatus> pf,
			ConcurrentKafkaListenerContainerFactory<String, OperationStatus> factory){
		KafkaTemplate<String, OperationStatus> kafkaTemplate = new KafkaTemplate<>(pf);
		factory.getContainerProperties().setMissingTopicsFatal(false);
		return kafkaTemplate;
	}

	@Bean
	public KafkaTemplate<String, Owner> getSingleOwnerReplyTemplate(
			ProducerFactory<String,Owner> pf,
			ConcurrentKafkaListenerContainerFactory<String, Owner> factory){
		KafkaTemplate<String, Owner> kafkaTemplate = new KafkaTemplate<>(pf);
		factory.getContainerProperties().setMissingTopicsFatal(false);
		return kafkaTemplate;
	}
}
