package no.group.petclinic.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.dto.OwnersPageRequest;
import no.group.petclinic.entity.Owner;

public interface OwnerService {
	
	public void getOwners(OwnersPageRequest request, byte[] correlationId);
	
	public void saveOrUpdateOwner(Owner owner, byte[] correlationId);

	public void getOwner(Integer ownerId, byte[] correlationId);

	public void deleteOwner(Integer ownerId);

}
