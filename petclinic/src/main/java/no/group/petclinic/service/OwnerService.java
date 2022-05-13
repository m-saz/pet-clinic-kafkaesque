package no.group.petclinic.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;

public interface OwnerService {
	
	public Page<OwnerSlim> getOwners(Pageable pageable);
	
	public Page<OwnerSlim> searchOwners(String keyword, Pageable pageable);
	
	public void saveOwner(Owner owner);

	public Owner getOwner(String ownerId);

	public void deleteOwner(String ownerId);

	public void updateOwner(String ownerId, Owner owner);
	
}
