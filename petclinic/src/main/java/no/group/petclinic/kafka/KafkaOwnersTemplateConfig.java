package no.group.petclinic.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageRequest;
import no.group.petclinic.dto.OwnersPageImpl;

@Configuration
public class KafkaOwnersTemplateConfig {

	@Value("${kafka.group.id}")
	private String groupId;

	@Bean
	public ReplyingKafkaTemplate<String, OwnersPageRequest, OwnersPageImpl<OwnerSlim>> ownersReplyingTemplate(
			ProducerFactory<String, OwnersPageRequest> pf,
			ConcurrentKafkaListenerContainerFactory<String, OwnersPageImpl<OwnerSlim>> factory){
		
		ConcurrentMessageListenerContainer<String, OwnersPageImpl<OwnerSlim>> repliesContainer =
				factory.createContainer(OwnerTopicConstants.OWNERS_REPLY);
		repliesContainer.getContainerProperties().setMissingTopicsFatal(false);
		repliesContainer.getContainerProperties().setGroupId(groupId);
		return new ReplyingKafkaTemplate<String, OwnersPageRequest, OwnersPageImpl<OwnerSlim>>(pf, repliesContainer);
	}
	
	@Bean
	public KafkaTemplate<String, OwnersPageImpl<OwnerSlim>> ownersTemplate(
			ProducerFactory<String,OwnersPageImpl<OwnerSlim>> pf,
			ConcurrentKafkaListenerContainerFactory<String, OwnersPageImpl<OwnerSlim>> factory){
		KafkaTemplate<String, OwnersPageImpl<OwnerSlim>> kafkaTemplate = new KafkaTemplate<>(pf);
		factory.getContainerProperties().setMissingTopicsFatal(false);
		return kafkaTemplate;
	}

}
