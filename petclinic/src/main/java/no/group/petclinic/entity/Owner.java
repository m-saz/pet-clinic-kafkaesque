package no.group.petclinic.entity;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import no.group.petclinic.dto.Pet;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Owner {
	
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String city;
	private String address;
	private Set<Pet> pets;
}
