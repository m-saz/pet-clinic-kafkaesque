package no.group.petclinic.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Visit {
	
	Integer id;
	Date visitDate;
	String description;
	
	@ToString.Exclude
	@JsonIgnore
	Pet pet;
}
