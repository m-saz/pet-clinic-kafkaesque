package no.group.petclinic.exception;

public class OwnerNotFoundException extends RuntimeException {
	public OwnerNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
