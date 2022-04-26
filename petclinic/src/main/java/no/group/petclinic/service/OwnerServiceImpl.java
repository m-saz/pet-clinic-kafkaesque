package no.group.petclinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OwnerSlim;
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

}
