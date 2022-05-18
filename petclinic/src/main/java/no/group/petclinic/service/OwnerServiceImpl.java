package no.group.petclinic.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OperationStatus;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageImpl;
import no.group.petclinic.dto.OwnersPageRequest;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;
import no.group.petclinic.entity.Visit;
import no.group.petclinic.exception.OwnerNotFoundException;
import no.group.petclinic.kafka.OwnerTopicConstants;
import no.group.petclinic.repository.OwnerRepository;

@Service
//@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

	public OwnerServiceImpl(OwnerRepository ownerRepository,
			KafkaTemplate<String, OwnersPageImpl<OwnerSlim>> pagedTemplate,
			KafkaTemplate<String, OperationStatus> statusTemplate,
			@Qualifier("getOne") KafkaTemplate<String, Owner> ownerTemplate) {
		this.ownerRepository = ownerRepository;
		this.pagedTemplate = pagedTemplate;
		this.statusTemplate = statusTemplate;
		this.ownerTemplate = ownerTemplate;
	}

	private final OwnerRepository ownerRepository;
	private final KafkaTemplate<String, OwnersPageImpl<OwnerSlim>> pagedTemplate;
	private final KafkaTemplate<String, OperationStatus> statusTemplate;
	private final KafkaTemplate<String, Owner> ownerTemplate;
	
	@KafkaListener(topics = OwnerTopicConstants.OWNERS_GET, groupId = "${kafka.group.id}")
	public void getOwners(OwnersPageRequest request, 
						@Header(KafkaHeaders.CORRELATION_ID) byte[] correlationId) {
		
		Page<OwnerSlim> owners = null;
		Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
		
		if(request.getKeyword() == null) {
			owners = ownerRepository.findAllOwners(pageable);
		} else {
			owners = ownerRepository.findOwnersByFirstNameOrLastName(
										request.getKeyword(), pageable);
		}
		
		OwnersPageImpl<OwnerSlim> payload = new OwnersPageImpl<OwnerSlim>(owners);
		
		Message<OwnersPageImpl<OwnerSlim>> message = MessageBuilder
				.withPayload(payload)
				.setHeader(KafkaHeaders.TOPIC, OwnerTopicConstants.OWNERS_GET_REPLY)
				.setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
				.build();
		pagedTemplate.send(message);
	}
	
	@Override
	@Transactional
	@KafkaListener(topics = OwnerTopicConstants.OWNER_SAVE, groupId = "${kafka.group.id}")
	public void saveOrUpdateOwner(Owner owner, @Header(KafkaHeaders.CORRELATION_ID) byte[] correlationId) {

		OperationStatus status = OperationStatus.OK;
		
		if(owner.getId() != null) {
			try {
				Owner existingOwner = ownerRepository.findById(owner.getId()).get();
			}
			catch(NoSuchElementException e) {
				status = OperationStatus.ERROR;
			}
		}
		
		setForeignKeys(owner);
		ownerRepository.save(owner);
		
		Message<OperationStatus> message = MessageBuilder
				.withPayload(status)
				.setHeader(KafkaHeaders.TOPIC, OwnerTopicConstants.OWNER_SAVE_REPLY)
				.setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
				.build();
		statusTemplate.send(message);
	}

	@Override
	@Transactional
	@KafkaListener(topics = OwnerTopicConstants.OWNER_GET_ONE, groupId = "${kafka.group.id}")
	public void getOwner(Integer ownerId,
						@Header(KafkaHeaders.CORRELATION_ID) byte[] correlationId){
		Owner owner = null;
		try {
			owner = ownerRepository.findById(ownerId).get();
		}
		catch(NoSuchElementException|NumberFormatException e) {
			e.printStackTrace();;
		}
		
		Message<Owner> message = MessageBuilder
				.withPayload(owner)
				.setHeader(KafkaHeaders.TOPIC, OwnerTopicConstants.OWNER_GET_ONE_REPLY)
				.setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
				.build();
		ownerTemplate.send(message);
	}

	@Override
	public void deleteOwner(Integer ownerId) {
		Owner existingOwner = new Owner();
		ownerRepository.deleteById(existingOwner.getId());
	}

	private static void setForeignKeys(Owner owner) {
		for(Pet pet: owner.getPets()) {
			if(pet.getVisits() != null) {
				for(Visit visit: pet.getVisits()) {
					visit.setPet(pet);
				}
			}
			pet.setOwner(owner);
		}
	}

}
