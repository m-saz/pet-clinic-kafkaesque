package no.group.petclinic.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Vet {
	
	private Integer id;
	private String firstName;
	private String lastName;
	private Set<Specialty> specialties;
}
