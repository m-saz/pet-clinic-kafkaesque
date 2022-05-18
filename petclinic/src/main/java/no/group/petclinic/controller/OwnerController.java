package no.group.petclinic.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import no.group.petclinic.dto.OwnersPageImpl;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.service.KafkaOwnerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners")
public class OwnerController {
	
	private final KafkaOwnerService ownerService;
	private final PagedResourcesAssembler<OwnerSlim> parAssembler;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping
	public ResponseEntity<PagedModel<EntityModel<OwnerSlim>>> getOwners(
										@RequestParam int page,
										@RequestParam int size) {
		
		Page<OwnerSlim> ownersPage = ownerService.getOwners(page, size);
		addSelfLinksToOwners(ownersPage);
		Link selfLink = linkTo(methodOn(OwnerController.class).getOwners(page, size)).withSelfRel();
		PagedModel<EntityModel<OwnerSlim>> ownerModel = parAssembler.toModel(ownersPage, selfLink);
		
		return ResponseEntity.ok().body(ownerModel);
	}

	@GetMapping("/search/findOwnersByFirstNameOrLastName")
	public ResponseEntity<PagedModel<EntityModel<OwnerSlim>>> searchOwners(
														@RequestParam int page,
														@RequestParam int size,
														@RequestParam String keyword
														) {
		
		Page<OwnerSlim> ownersPage = ownerService.getOwners(page, size, keyword);
		addSelfLinksToOwners(ownersPage);
		Link selfLink = linkTo(methodOn(OwnerController.class).searchOwners(page, size, keyword))
								.withSelfRel();
		PagedModel<EntityModel<OwnerSlim>> ownerModel = parAssembler.toModel(ownersPage, selfLink);
		
		return ResponseEntity.ok().body(ownerModel);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Owner> getOwner(@PathVariable Integer id) {
		
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
	public ResponseEntity updateOwner(@PathVariable Integer id, @RequestBody Owner owner) {
		logger.info("{} {}",id, owner);
		ownerService.updateOwner(id, owner);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteOwner(@PathVariable Integer id) {
		ownerService.deleteOwner(id);
		return ResponseEntity.noContent().build();
	}
	
	private static void addSelfLinksToOwners(Page<OwnerSlim> ownersPage) {
		
		if(ownersPage.getContent() != null) {
			for(final OwnerSlim owner : ownersPage.getContent()) {
				
				Link selfLink = linkTo(methodOn(OwnerController.class)
										.getOwner(owner.getId()))
										.withSelfRel();
				owner.add(selfLink);
			}
		}
	}
}
