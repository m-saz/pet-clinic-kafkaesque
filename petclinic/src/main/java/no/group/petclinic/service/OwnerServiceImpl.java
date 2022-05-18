package no.group.petclinic.service;

import java.util.NoSuchElementException;

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
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageRequest;
import no.group.petclinic.dto.OwnersPageImpl;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;
import no.group.petclinic.entity.Visit;
import no.group.petclinic.exception.OwnerNotFoundException;
import no.group.petclinic.kafka.OwnerTopicConstants;
import no.group.petclinic.repository.OwnerRepository;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	private final KafkaTemplate<String, OwnersPageImpl<OwnerSlim>> pagedTemplate;
	
	@KafkaListener(topics = OwnerTopicConstants.OWNERS, groupId = "${kafka.group.id}")
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
				.setHeader(KafkaHeaders.TOPIC, OwnerTopicConstants.OWNERS_REPLY)
				.setHeader(KafkaHeaders.CORRELATION_ID, correlationId)
				.build();
		pagedTemplate.send(message);
	}
	
	@Override
	@Transactional
	public void saveOwner(Owner owner) {

		setForeignKeys(owner);
		
		ownerRepository.save(owner);
	}

	@Override
	public Owner getOwner(Integer ownerId) {
		Owner owner = null;
		try {
			owner = ownerRepository.findById(ownerId).get();
		}
		catch(NoSuchElementException|NumberFormatException e) {
			throw new OwnerNotFoundException("Can't find Owner with id: "+ownerId);
		}
		
		return owner;
	}

	@Override
	public void deleteOwner(Integer ownerId) {
		Owner existingOwner = getOwner(ownerId);
		ownerRepository.deleteById(existingOwner.getId());
	}

	@Override
	@Transactional
	public void updateOwner(Integer ownerId, Owner owner) {
		Owner existingOwner = getOwner(ownerId);
		
		owner.setId(existingOwner.getId());
		
		setForeignKeys(owner);
		
		ownerRepository.save(owner);
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
