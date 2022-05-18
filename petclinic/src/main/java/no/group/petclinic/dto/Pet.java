package no.group.petclinic.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import no.group.petclinic.entity.Owner;

@Getter
@Setter
@ToString
public class Pet {
	
	private Integer id;
	private String name;
	private Date birthDate;
	private Type type;
	
	@ToString.Exclude
	@JsonIgnore
	private Owner owner;
	private List<Visit> visits;
	
}
