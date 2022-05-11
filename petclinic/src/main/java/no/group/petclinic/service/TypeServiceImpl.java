package no.group.petclinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Type;
import no.group.petclinic.repository.TypeRepository;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

	private final TypeRepository typeRepository;
	
	@Override
	public List<Type> getTypes() {
		return typeRepository.findAll();
	}

}
