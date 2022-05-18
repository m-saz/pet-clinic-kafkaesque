package no.group.petclinic.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import no.group.petclinic.dto.Type;
import no.group.petclinic.service.KafkaTypeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/types")
public class TypeController {
	
	private final KafkaTypeService typeService;
	
	@GetMapping
	public ResponseEntity<List<Type>> getTypes(){
		List<Type> types = typeService.getTypes();
		return ResponseEntity.ok().body(types);
	}
	
}
