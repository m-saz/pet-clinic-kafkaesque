package no.group.petclinic.service;

import java.util.List;

import no.group.petclinic.dto.Vet;

public interface KafkaVetService {

	public List<Vet> getVets();
	
}
