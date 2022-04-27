package no.group.petclinic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;
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

}
