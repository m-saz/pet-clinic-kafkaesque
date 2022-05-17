package no.group.petclinic.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import no.group.petclinic.entity.Type;

public interface KafkaTypeService {
	public List<Type> getTypes();
}
