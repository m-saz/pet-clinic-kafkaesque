package no.group.petclinic.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.service.OwnerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners")
public class OwnerController {
	
	private final OwnerService ownerService;
	
	@GetMapping
	public List<Owner> getOwners() {
		
		return ownerService.getOwners();
		
	}
	
}
