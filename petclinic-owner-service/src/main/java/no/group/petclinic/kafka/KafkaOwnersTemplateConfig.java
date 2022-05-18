package no.group.petclinic.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import no.group.petclinic.dto.OperationStatus;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageImpl;
import no.group.petclinic.dto.OwnersPageRequest;
import no.group.petclinic.entity.Owner;

@Configuration
public class KafkaOwnersTemplateConfig {

	@Bean
	public KafkaTemplate<String, OwnersPageImpl<OwnerSlim>> getOwnersReplyTemplate(
			ProducerFactory<String,OwnersPageImpl<OwnerSlim>> pf,
			ConcurrentKafkaListenerContainerFactory<String, OwnersPageImpl<OwnerSlim>> factory){
		KafkaTemplate<String, OwnersPageImpl<OwnerSlim>> kafkaTemplate = new KafkaTemplate<>(pf);
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
