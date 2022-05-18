package no.group.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;

@DataJpaTest
@Sql(statements = "ALTER TABLE owners ALTER COLUMN id RESTART WITH 1",
		executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class OwnerRepositoryTest {
	
	@Autowired
	private OwnerRepository underTest;
	
	@AfterEach
	private void tearDown() {
		underTest.deleteAll();
	}
	
	@Test
	@DisplayName("findAllOwners() -> finds owners")
	void itShouldFindAllOwners() {
		
		//given
		Owner owner = createOwnerWithNameAndContacts("Test","Subject","phone","email");
		saveToDatabase(owner);
		Pageable pageable = PageRequest.of(0, 10);
		
		//when
		Page<OwnerSlim> actual = underTest.findAllOwners(pageable);
		
		//then
		OwnerSlim expected = new OwnerSlim(1,"Test","Subject","phone","email");
		assertThat(actual.getContent()).containsExactly(expected);
	}
	
	@Test
	@DisplayName("findOwnersByFirstNameOrLastName() -> finds only owners matching"
			+ "the keyword")
	void itShouldFindOwnersByKeyword() {
		
		//given
		Owner ownerFound = createOwnerWithNameAndContacts("Test","Subject","phone","email");
		Owner ownerNotFound = createOwnerWithNameAndContacts("Test","Object","phon","emai");
		Owner ownerFoundToo = createOwnerWithNameAndContacts("Subj","Testect","pho","ema");
		saveToDatabase(ownerFound, ownerNotFound, ownerFoundToo);
		Pageable pageable = PageRequest.of(0, 10);
		
		//when
		Page<OwnerSlim> actual = underTest.findOwnersByFirstNameOrLastName("sub", pageable);
		
		//then
		OwnerSlim expected = new OwnerSlim(1,"Test","Subject","phone","email");
		OwnerSlim expectedToo = new OwnerSlim(3, "Subj", "Testect", "pho", "ema");
		assertThat(actual.getContent()).containsExactly(expected, expectedToo);
	}
	
	private Owner createOwnerWithNameAndContacts(String firstName, String lastName,
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
	
	private void saveToDatabase(Owner ... owners) {
		for(Owner owner: owners) {
			underTest.save(owner);
		}
	}

}
