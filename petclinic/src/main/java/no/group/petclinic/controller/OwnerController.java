package no.group.petclinic.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import no.group.petclinic.entity.Owner;
import no.group.petclinic.service.OwnerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners")
public class OwnerController {
	
	private final OwnerService ownerService;
	private final PagedResourcesAssembler<OwnerSlim> parAssembler;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping
	public ResponseEntity<PagedModel<EntityModel<OwnerSlim>>> getOwners(@PageableDefault Pageable pageable) {
		
		Page<OwnerSlim> ownersPage = ownerService.getOwners(pageable);
		addSelfLinksToOwners(ownersPage);
		Link selfLink = linkTo(methodOn(OwnerController.class).getOwners(pageable)).withSelfRel();
		PagedModel<EntityModel<OwnerSlim>> ownerModel = parAssembler.toModel(ownersPage, selfLink);
		
		return ResponseEntity.ok().body(ownerModel);
	}

	@GetMapping("/search/findOwnersByFirstNameOrLastName")
	public ResponseEntity<PagedModel<EntityModel<OwnerSlim>>> searchOwners(@RequestParam("keyword") String keyword,
														@PageableDefault Pageable pageable) {
		
		Page<OwnerSlim> ownersPage = ownerService.searchOwners(keyword, pageable);
		addSelfLinksToOwners(ownersPage);
		Link selfLink = linkTo(methodOn(OwnerController.class).searchOwners(keyword, pageable))
								.withSelfRel();
		PagedModel<EntityModel<OwnerSlim>> ownerModel = parAssembler.toModel(ownersPage, selfLink);
		
		return ResponseEntity.ok().body(ownerModel);
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
	
	private static void addSelfLinksToOwners(Page<OwnerSlim> ownersPage) {
		
		if(ownersPage.getContent() != null) {
			for(final OwnerSlim owner : ownersPage.getContent()) {
				
				Link selfLink = linkTo(methodOn(OwnerController.class)
										.getOwner(owner.getId().toString()))
										.withSelfRel();
				owner.add(selfLink);
			}
		}
	}
}
