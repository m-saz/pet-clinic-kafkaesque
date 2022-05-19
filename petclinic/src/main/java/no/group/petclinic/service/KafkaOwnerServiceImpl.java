package no.group.petclinic.service;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OperationStatus;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageRequest;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.exception.OwnerNotFoundException;
import no.group.petclinic.kafka.OwnerTopicConstants;

@Service
@RequiredArgsConstructor
public class KafkaOwnerServiceImpl implements KafkaOwnerService{

	private final ReplyingKafkaTemplate<String, OwnersPageRequest, Page<OwnerSlim>> getPagedOwnersTemplate;
	private final ReplyingKafkaTemplate<String, Owner, OperationStatus> saveOwnerTemplate;
	private final ReplyingKafkaTemplate<String, Integer, Owner> getSingleOwnerTemplate;
	private final ReplyingKafkaTemplate<String, Integer, OperationStatus> deleteOwnerTemplate;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Override
	public Page<OwnerSlim> getOwners(int page, int size, String keyword) {
		
		OwnersPageRequest request = new OwnersPageRequest(keyword, page, size);
		ProducerRecord<String, OwnersPageRequest> record =
				new ProducerRecord<>(OwnerTopicConstants.OWNERS_GET, request);
		RequestReplyFuture<String, OwnersPageRequest, Page<OwnerSlim>> future = 
				getPagedOwnersTemplate.sendAndReceive(record);
		ConsumerRecord<String, Page<OwnerSlim>> response = null;
		try {
			response = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return response.value();
	}
	
	@Override
	public Page<OwnerSlim> getOwners(int page, int size) {
		return getOwners(page, size, null);
	}
	
	@Override
	public Owner getOwner(Integer ownerId) {
		ProducerRecord<String, Integer> record =
				new ProducerRecord<>(OwnerTopicConstants.OWNER_GET_ONE, ownerId);
		RequestReplyFuture<String, Integer, Owner> future =
				getSingleOwnerTemplate.sendAndReceive(record);
		ConsumerRecord<String, Owner> response = null;
		try {
			response = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		if(response.value() == null) {
			throw new OwnerNotFoundException("Unable to find Owner with id: "+ownerId);
		}
		return response.value();
	}

	@Override
	public OperationStatus saveOwner(Owner owner) {
		ProducerRecord<String, Owner> record = 
				new ProducerRecord<>(OwnerTopicConstants.OWNER_SAVE, owner);
		RequestReplyFuture<String, Owner, OperationStatus> future =
				saveOwnerTemplate.sendAndReceive(record);
		ConsumerRecord<String, OperationStatus> response = null;
		try {
			response = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		LOG.info("{}", response.value().toString());
		return response.value();
		
	}
	
	@Override
	public OperationStatus updateOwner(Integer ownerId, Owner owner) {
		owner.setId(ownerId);
		OperationStatus status = saveOwner(owner);
		if(status == OperationStatus.ERROR) {
			throw new OwnerNotFoundException("Unable to find Owner with id: "+ownerId);
		}
		return status;
	}

	@Override
	public OperationStatus deleteOwner(Integer ownerId) {
		ProducerRecord<String, Integer> record = 
				new ProducerRecord<>(OwnerTopicConstants.OWNER_DELETE, ownerId);
		RequestReplyFuture<String, Integer, OperationStatus> future =
				deleteOwnerTemplate.sendAndReceive(record);
		ConsumerRecord<String, OperationStatus> response = null;
		try {
			response = future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		OperationStatus status = response.value();
		if(status == OperationStatus.ERROR) {
			throw new OwnerNotFoundException("Unable to find Owner with id: "+ownerId);
		}
		LOG.info("{}", status);
		return status;
	}

}
