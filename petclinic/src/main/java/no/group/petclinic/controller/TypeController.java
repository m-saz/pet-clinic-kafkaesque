package no.group.petclinic.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.entity.Type;
import no.group.petclinic.service.TypeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/types")
public class TypeController {
	
	private final TypeService typeService;
	
	@GetMapping
	public List<Type> getTypes(){
		return typeService.getTypes();
	}
	
}
