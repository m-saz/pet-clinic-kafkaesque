package no.group.petclinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Vet;
import no.group.petclinic.repository.VetRepository;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

	private final VetRepository vetRepository;
	
	@Override
	public List<Vet> getVets() {
		return vetRepository.findAll();
	}

}
