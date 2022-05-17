package no.group.petclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TypesNotFoundException extends RuntimeException {

	public TypesNotFoundException(String message) {
		super(message);
	}
}
