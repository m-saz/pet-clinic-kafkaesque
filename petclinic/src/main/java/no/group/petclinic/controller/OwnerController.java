package no.group.petclinic.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.service.OwnerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners")
@CrossOrigin(origins = "http://localhost:4200")
public class OwnerController {
	
	private final OwnerService ownerService;
	
	@GetMapping
	public List<OwnerSlim> getOwners() {
		
		return ownerService.getOwners();
		
	}
	
	@GetMapping("/search/findByFirstNameContainingOrLastNameContainingAllIgnoreCase")
	public List<OwnerSlim> searchOwners(@RequestParam("keyword") String keyword) {
		
		return ownerService.searchOwners(keyword);
		
	}
	
}
