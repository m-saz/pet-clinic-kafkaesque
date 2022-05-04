package no.group.petclinic.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.exception.OwnerNotFoundException;
import no.group.petclinic.service.OwnerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners")
public class OwnerController {
	
	private final OwnerService ownerService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping
	public ResponseEntity<List<OwnerSlim>> getOwners() {
		
		List<OwnerSlim> owners = ownerService.getOwners();
		return ResponseEntity.ok().body(owners);
	}
	
	@GetMapping("/search/findOwnersByFirstNameOrLastName")
	public ResponseEntity<List<OwnerSlim>> searchOwners(@RequestParam("keyword") String keyword) {
		
		List<OwnerSlim> owners = ownerService.searchOwners(keyword);
		return ResponseEntity.ok().body(owners);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Owner> getOwner(@PathVariable String id) {
		
		Owner owner = ownerService.getOwner(id);
		return ResponseEntity.ok().body(owner);
	}
	
	@PostMapping
	public ResponseEntity processOwner(@RequestBody Owner owner) {
		logger.info("{}",owner);
		ownerService.saveOwner(owner);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateOwner(@PathVariable String id, @RequestBody Owner owner) {
		logger.info("{} {}",id, owner);
		ownerService.updateOwner(id, owner);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteOwner(@PathVariable String id) {
		ownerService.deleteOwner(id);
		return ResponseEntity.noContent().build();
	}
	
}
