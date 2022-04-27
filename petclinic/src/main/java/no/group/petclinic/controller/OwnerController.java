package no.group.petclinic.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.service.OwnerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners")
public class OwnerController {
	
	private final OwnerService ownerService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping
	public List<OwnerSlim> getOwners() {
		
		return ownerService.getOwners();
	}
	
	@GetMapping("/search/findByFirstNameContainingOrLastNameContainingAllIgnoreCase")
	public List<OwnerSlim> searchOwners(@RequestParam("keyword") String keyword) {
		
		return ownerService.searchOwners(keyword);
	}
	
	@PostMapping
	public void processOwner(@RequestBody Owner owner) {
		logger.info(owner.toString());
		ownerService.saveOwner(owner);
	}
	
}
