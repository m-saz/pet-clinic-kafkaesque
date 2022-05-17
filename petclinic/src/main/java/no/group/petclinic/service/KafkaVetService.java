package no.group.petclinic.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import no.group.petclinic.entity.Vet;

public interface KafkaVetService {

	public List<Vet> getVets() throws InterruptedException, ExecutionException;
	
}
