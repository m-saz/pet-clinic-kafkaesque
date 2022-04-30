package no.group.petclinic.service;

import java.util.List;

import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;

public interface OwnerService {
	
	public List<OwnerSlim> getOwners();
	
	public List<OwnerSlim> searchOwners(String keyword);
	
	public void saveOwner(Owner owner);

	public Owner getOwner(String ownerId);

	public void deleteOwner(String ownerId);

	public void updateOwner(String ownerId, Owner owner);
	
}
