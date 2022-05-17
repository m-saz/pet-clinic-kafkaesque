package no.group.petclinic.service;

import java.util.List;

import no.group.petclinic.entity.Vet;

public interface KafkaVetService {

	public List<Vet> getVets();
	
}
