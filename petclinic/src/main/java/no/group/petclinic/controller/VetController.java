package no.group.petclinic.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Vet;
import no.group.petclinic.service.VetService;

@RestController
@RequestMapping("/api/vets")
@RequiredArgsConstructor
public class VetController {
	
	private final VetService vetService;
	
	@GetMapping
	public List<Vet> getVets(){
		return vetService.getVets();
	}

}
