package no.group.petclinic.service;

import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageImpl;
import no.group.petclinic.entity.Owner;

public interface KafkaOwnerService {
	
	public OwnersPageImpl<OwnerSlim> getOwners(int page, int size);
	
	public OwnersPageImpl<OwnerSlim> getOwners(int page, int size, String keyword);
	
	public void saveOwner(Owner owner);

	public Owner getOwner(Integer ownerId);

	public void deleteOwner(Integer ownerId);

	public void updateOwner(Integer ownerId, Owner owner);
}
