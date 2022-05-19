package no.group.petclinic.dto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "owners")
public class OwnerSlim extends EntityModel<OwnerSlim>{
	
	private Integer id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;

}
