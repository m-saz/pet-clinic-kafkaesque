package no.group.petclinic.service;

import java.util.List;

import no.group.petclinic.dto.Type;

public interface KafkaTypeService {
	public List<Type> getTypes();
}
