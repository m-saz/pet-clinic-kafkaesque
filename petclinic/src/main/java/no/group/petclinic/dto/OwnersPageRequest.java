package no.group.petclinic.dto;

import org.springframework.data.domain.PageRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnersPageRequest {
	
	String keyword;
	int page;
	int size;
}
