package no.group.petclinic.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import no.group.petclinic.dto.OperationStatus;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageRequest;
import no.group.petclinic.entity.Owner;

@Configuration
public class KafkaOwnersTemplateConfig {

	@Value("${kafka.group.id}")
	private String groupId;
	
	@Value("${spring.kafka.bootstrap-servers[0]}")
	private String bootstrapServers;
	
	@Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
	private String trustedPackages;

	@Bean
	public ReplyingKafkaTemplate<String, OwnersPageRequest, Page<OwnerSlim>> getOwnersReplyingTemplate(
			ProducerFactory<String, OwnersPageRequest> pf,
			@Qualifier("pageFactory") ConcurrentKafkaListenerContainerFactory<String, Page<OwnerSlim>> factory){
		ConcurrentMessageListenerContainer<String, Page<OwnerSlim>> repliesContainer =
				factory.createContainer(OwnerTopicConstants.OWNERS_GET_REPLY);
		repliesContainer.getContainerProperties().setMissingTopicsFatal(false);
		repliesContainer.getContainerProperties().setGroupId(groupId);
		return new ReplyingKafkaTemplate<String, OwnersPageRequest, Page<OwnerSlim>>(pf, repliesContainer);
	}
	
	@Bean
	public ReplyingKafkaTemplate<String, Owner, OperationStatus> saveOwnersReplyingTemplate(
			ProducerFactory<String, Owner> pf,
			ConcurrentKafkaListenerContainerFactory<String, OperationStatus> factory){
		
		ConcurrentMessageListenerContainer<String, OperationStatus> repliesContainer =
				factory.createContainer(OwnerTopicConstants.OWNER_SAVE_REPLY);
		repliesContainer.getContainerProperties().setMissingTopicsFatal(false);
		repliesContainer.getContainerProperties().setGroupId(groupId);
		return new ReplyingKafkaTemplate<String, Owner, OperationStatus>(pf, repliesContainer);
	}
	
	@Bean
	public ReplyingKafkaTemplate<String, Integer, Owner> getSingleOwnerReplyingTemplate(
			ProducerFactory<String, Integer> pf,
			ConcurrentKafkaListenerContainerFactory<String, Owner> factory){
		
		ConcurrentMessageListenerContainer<String, Owner> repliesContainer =
				factory.createContainer(OwnerTopicConstants.OWNER_GET_ONE_REPLY);
		repliesContainer.getContainerProperties().setMissingTopicsFatal(false);
		repliesContainer.getContainerProperties().setGroupId(groupId);
		return new ReplyingKafkaTemplate<String, Integer, Owner>(pf, repliesContainer);
	}
	
	@Bean
	public ReplyingKafkaTemplate<String, Integer, OperationStatus> deleteOwnerReplyingTemplate(
			ProducerFactory<String, Integer> pf,
			ConcurrentKafkaListenerContainerFactory<String, OperationStatus> factory){
		
		ConcurrentMessageListenerContainer<String, OperationStatus> repliesContainer =
				factory.createContainer(OwnerTopicConstants.OWNER_DELETE_REPLY);
		repliesContainer.getContainerProperties().setMissingTopicsFatal(false);
		repliesContainer.getContainerProperties().setGroupId(groupId);
		return new ReplyingKafkaTemplate<String, Integer, OperationStatus>(pf, repliesContainer);
	}
	
	@Bean
	@Primary
	ConcurrentKafkaListenerContainerFactory kafkaDefaultListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory factory = 
				new ConcurrentKafkaListenerContainerFactory();
		factory.setConsumerFactory(defaultConsumerFactory());
		return factory;
	}
	
	@Bean(name = "pageFactory")
	ConcurrentKafkaListenerContainerFactory kafkaPageListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory factory = 
				new ConcurrentKafkaListenerContainerFactory();
		factory.setConsumerFactory(pageConsumerFactory());
		return factory;
	}
	
	@Bean
	@Primary
	ConsumerFactory defaultConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
		return new DefaultKafkaConsumerFactory<String, Page<OwnerSlim>>(props);
	}
	
	@Bean
	public ConsumerFactory<String, Page<OwnerSlim>> pageConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PageDeserializer.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
		return new DefaultKafkaConsumerFactory<String, Page<OwnerSlim>>(props);
	}
}
