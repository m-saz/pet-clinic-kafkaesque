package no.group.petclinic.service;

import java.util.List;

import no.group.petclinic.dto.OwnerSlim;

public interface OwnerService {
	
	public List<OwnerSlim> getOwners();
	
	public List<OwnerSlim> searchOwners(String keyword);
	
}
