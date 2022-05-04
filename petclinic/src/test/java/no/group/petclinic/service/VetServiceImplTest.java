package no.group.petclinic.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import no.group.petclinic.repository.VetRepository;

@ExtendWith(MockitoExtension.class)
class VetServiceImplTest {

	@Mock
	private VetRepository vetRepository;
	
	private VetServiceImpl underTest;
	
	@BeforeEach
	void setup() {
		underTest = new VetServiceImpl(vetRepository);
	}
	
	@Test
	@DisplayName("getVets() -> will call findAll()")
	void testGetVets() {
		
		//when
		underTest.getVets();
		
		//then
		verify(vetRepository).findAll();
	}

}
