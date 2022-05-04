package no.group.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;
import no.group.petclinic.exception.OwnerNotFoundException;
import no.group.petclinic.repository.OwnerRepository;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceImplTest {

	private OwnerServiceImpl underTest;
	
	@Mock
	private OwnerRepository ownerRepository;
	
	@BeforeEach
	void setUp() {
		underTest = new OwnerServiceImpl(ownerRepository);
	}
	
	@Test
	@DisplayName("getOwners() -> findAllOwners() method is called")
	void canGetAllOwners() {
		
		//when
		underTest.getOwners();
		
		//then
		verify(ownerRepository).findAllOwners();
	}
	
	@Test
	@DisplayName("searchOwners() -> findOwnersByFirstNameOrLastName() method is called"
			+ "with correct keyword")
	void canSearchOwners() {
		
		//given
		String keyword = "test";
		
		//when
		underTest.searchOwners(keyword);
		
		//then
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
		verify(ownerRepository).
				findOwnersByFirstNameOrLastName(argumentCaptor.capture());
		String capturedKeyword = argumentCaptor.getValue();
		assertThat(capturedKeyword).isEqualTo(keyword);
		
	}
	
	@Test
	@DisplayName("saveOwner() -> save() method is called with correct owner")
	void canSaveOwner() {
		
		//given
		Owner owner = createOwner("Test", "Subject", "Phone Number", "Email");
		
		//when
		underTest.saveOwner(owner);
		
		//then
		ArgumentCaptor<Owner> argumentCaptor = ArgumentCaptor.forClass(Owner.class);
		verify(ownerRepository).save(argumentCaptor.capture());
		Owner capturedOwner = argumentCaptor.getValue();
		assertThat(capturedOwner).isEqualTo(owner);
		
	}
	
	@Nested
	@DisplayName("getOwner() -> ")
	class GetOwnerTest{
		
		@Test
		@DisplayName("findById() method is called with correct id")
		void canGetOwner() {
			
			//given
			String id = "23";
			when(ownerRepository.findById(23))
					.thenReturn(Optional.of(new Owner()));
			
			//when
			underTest.getOwner(id);
			
			//then
			ArgumentCaptor<Integer> argumentCaptor =
							ArgumentCaptor.forClass(Integer.class);
			verify(ownerRepository).findById(argumentCaptor.capture());
			Integer capturedInteger = argumentCaptor.getValue();
			assertThat(capturedInteger).isEqualTo(23);
		}
		
		@Test
		@DisplayName("throws OwnerNotFoundException when owner not found")
		void throwsWhenOwnerNotFound() {
			
			//given
			String id = "23";
			when(ownerRepository.findById(23))
					.thenReturn(Optional.empty());
			
			//when
			//then
			assertThatThrownBy(() -> underTest.getOwner(id))
					.isInstanceOf(OwnerNotFoundException.class)
					.hasMessageContaining("Can't find Owner with id: "+id);
		}
		
		@Test
		@DisplayName("throws OwnerNotFoundException when id is not a number")
		void throwsWhenIdIsNotANumber() {
			
			//given
			String id = "test";
			
			//when
			//then
			assertThatThrownBy(() -> underTest.getOwner(id))
					.isInstanceOf(OwnerNotFoundException.class)
					.hasMessageContaining("Can't find Owner with id: "+id);
		}
		
	}
	
	@Test
	@DisplayName("deleteOwner() -> deleteById() method is called with correct id")
	void canDeleteOwner() {
		
		//given
		String ownerId = "15";
		Integer id = 15;
		Owner queryResult = createOwner("First", "Last", "Phone", "Email");
		queryResult.setId(id);
		when(ownerRepository.findById(id)).thenReturn(Optional.of(queryResult));
		
		//when
		underTest.deleteOwner(ownerId);
		
		//then
		ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(ownerRepository).deleteById(argumentCaptor.capture());
		Integer capturedInteger = argumentCaptor.getValue();
		assertThat(capturedInteger).isEqualTo(id);
	}
	
	@Test
	@DisplayName("updateOwner() -> save() method is called with correct owner")
	void canUpdateOwner() {
		
		//given
		String ownerId = "123";
		Integer id = 123;
		Owner owner = createOwner("First", "Last", "Phone", "Email");
		owner.setId(id);
		when(ownerRepository.findById(id)).thenReturn(Optional.of(owner));
		
		//when
		underTest.updateOwner(ownerId, owner);
		
		//then
		ArgumentCaptor<Owner> argumentCaptor = ArgumentCaptor.forClass(Owner.class);
		verify(ownerRepository).save(argumentCaptor.capture());
		Owner capturedOwner = argumentCaptor.getValue();
		assertThat(capturedOwner).isEqualTo(owner);
	}
	
	private Owner createOwner(String firstName, String lastName,
			String phoneNumber, String email) {
		
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setPhoneNumber(phoneNumber);
		owner.setEmail(email);
		owner.setCity("Somewhere");
		owner.setAddress("Anywhere");
		owner.setPets(new HashSet<Pet>());
		return owner;
	}	
	
	
}
