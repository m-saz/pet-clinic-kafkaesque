package no.group.petclinic.service;

import no.group.petclinic.dto.OperationStatus;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageImpl;
import no.group.petclinic.entity.Owner;

public interface KafkaOwnerService {
	
	public OwnersPageImpl<OwnerSlim> getOwners(int page, int size);
	
	public OwnersPageImpl<OwnerSlim> getOwners(int page, int size, String keyword);
	
	public OperationStatus saveOwner(Owner owner);

	public Owner getOwner(Integer ownerId);

	public OperationStatus deleteOwner(Integer ownerId);

	public OperationStatus updateOwner(Integer ownerId, Owner owner);
}
