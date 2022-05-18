package no.group.petclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class OwnerSlim{
	
	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
	
	private String email;

}
