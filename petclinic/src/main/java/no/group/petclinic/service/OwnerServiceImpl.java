package no.group.petclinic.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;
import no.group.petclinic.entity.Visit;
import no.group.petclinic.exception.OwnerNotFoundException;
import no.group.petclinic.repository.OwnerRepository;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	
	@Override
	public List<OwnerSlim> getOwners() {
		return ownerRepository.findAllOwners();
	}

	@Override
	public List<OwnerSlim> searchOwners(String keyword) {
		return ownerRepository.findOwnersByFirstNameOrLastName(keyword);
	}

	@Override
	@Transactional
	public void saveOwner(Owner owner) {
		//Set foreign keys
		for(Pet tempPet: owner.getPets()) {
			tempPet.setOwner(owner);
		}
		
		ownerRepository.save(owner);
	}

	@Override
	public Owner getOwner(String ownerId) {
		Owner owner = null;
		try {
			Integer id = Integer.parseInt(ownerId);
			owner = ownerRepository.findById(id).get();
		}
		catch(NoSuchElementException|NumberFormatException e) {
			throw new OwnerNotFoundException("Can't find Owner with id: "+ownerId);
		}
		
		return owner;
	}

	@Override
	public void deleteOwner(String ownerId) {
		Owner existingOwner = getOwner(ownerId);
		ownerRepository.deleteById(existingOwner.getId());
	}

	@Override
	@Transactional
	public void updateOwner(String ownerId, Owner owner) {
		Owner existingOwner = getOwner(ownerId);
		
		owner.setId(existingOwner.getId());
		
		//Set foreign keys
		for(Pet pet: owner.getPets()) {
			for(Visit visit: pet.getVisits()) {
				visit.setPet(pet);
			}
			pet.setOwner(owner);
		}
		
		ownerRepository.save(owner);
	}

}
