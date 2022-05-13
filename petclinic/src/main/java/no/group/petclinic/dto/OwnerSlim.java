package no.group.petclinic.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OwnerSlim extends EntityModel<OwnerSlim>{
	
	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private String email;

}
