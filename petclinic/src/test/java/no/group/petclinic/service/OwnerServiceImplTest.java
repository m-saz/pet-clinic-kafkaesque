package no.group.petclinic.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;
import no.group.petclinic.entity.Visit;
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
		
		//given
		Pageable pageable = PageRequest.of(0, 10);
		
		//when
		underTest.getOwners(pageable);
		
		//then
		verify(ownerRepository).findAllOwners(pageable);
	}
	
	@Test
	@DisplayName("searchOwners() -> given Pageable and keyword findOwnersByFirstNameOrLastName()"
			+ " method is called with correct keyword")
	void canSearchOwners() {
		
		//given
		String keyword = "test";
		Pageable pageable = PageRequest.of(0, 10);
		
		//when
		underTest.searchOwners(keyword, pageable);
		
		//then
		verify(ownerRepository).
				findOwnersByFirstNameOrLastName(keyword, pageable);
		
	}
	
	@Nested
	@DisplayName("saveOwner() -> ")
	class SaveOwnerTest{
		
		@Test
		@DisplayName("save() method is called with correct owner")
		void canSaveOwner() {
			
			//given
			Owner owner = createOwner("Test", "Subject", "Phone Number", "Email");
			
			//when
			underTest.saveOwner(owner);
			
			//then
			verify(ownerRepository).save(owner);
			
		}
		
		@Test
		@DisplayName("Pet.setOwner() method is called with correct owner")
		void canSetOwner() {
			
			//given
			Owner owner = createOwner("Test", "Subject", "Phone Number", "Email");
			Pet mockPet = mock(Pet.class);
			owner.getPets().add(mockPet);
			
			//when
			underTest.saveOwner(owner);
			
			//then
			verify(mockPet, times(1)).setOwner(owner);
		}
		
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
			verify(ownerRepository).findById(23);
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
		verify(ownerRepository).deleteById(id);
	}
	
	@Nested
	@DisplayName("updateOwner() -> ")
	class UpdateOwnerTest{
		
		@Test
		@DisplayName("save() method is called with correct owner")
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
			verify(ownerRepository).save(owner);
		}
		
		@Test
		@DisplayName("Pet.setOwner() and Visit.setPet() methods are called")
		void canSetOwnerAndSetPet() {
			
			//given
			String ownerId = "123";
			Integer id = 123;
			
			Visit visit = mock(Visit.class);
			
			Pet pet = spy(Pet.class);
			pet.setVisits(List.of(visit));
			
			Owner owner = createOwner("First", "Last", "Phone", "Email");
			owner.setId(id);
			owner.getPets().add(pet);
			
			when(ownerRepository.findById(id)).thenReturn(Optional.of(owner));
			
			//when
			underTest.updateOwner(ownerId, owner);
			
			//then
			verify(pet).setOwner(owner);
			verify(visit).setPet(pet);
		}
		
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
