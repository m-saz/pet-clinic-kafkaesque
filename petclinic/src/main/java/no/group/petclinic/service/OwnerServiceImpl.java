package no.group.petclinic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;
import no.group.petclinic.exception.OwnerNotFoundException;
import no.group.petclinic.repository.OwnerRepository;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	
	@Override
	public List<OwnerSlim> getOwners() {
		return ownerRepository.findOwners();
	}

	@Override
	public List<OwnerSlim> searchOwners(String keyword) {
		return ownerRepository.findOwnersByFirstNameOrLastName(keyword);
	}

	@Override
	@Transactional
	public void saveOwner(Owner owner) {
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
		catch(Exception e) {
			throw new OwnerNotFoundException("Can't find Owner with id: "+ownerId);
		}
		
		return owner;
	}

	@Override
	public void deleteOwner(String ownerId) {
		Integer id = Integer.parseInt(ownerId);
		if(!(ownerRepository.existsById(id))) {
			throw new OwnerNotFoundException("Can't find Owner with id: "+ownerId);
		}
		ownerRepository.deleteById(id);
	}

}
