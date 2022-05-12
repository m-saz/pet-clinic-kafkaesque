package no.group.petclinic.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import no.group.petclinic.entity.Specialty;
import no.group.petclinic.entity.Vet;
import no.group.petclinic.service.VetService;

@WebMvcTest(VetController.class)
class VetControllerTest {

	@MockBean
	private VetService vetService;
	
	@MockBean
	private JwtDecoder jwtDecoder;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	@DisplayName("getVets() -> given vets and JWT will return json array")
	void getVets_canReturnVetsJsonArray() throws Exception {
		
		//given
		Vet vet = new Vet();
		vet.setId(11);
		vet.setFirstName("Test");
		vet.setLastName("Subject");
		vet.setSpecialties(new HashSet<Specialty>());
		List<Vet> vets = List.of(vet);
		
		when(vetService.getVets()).thenReturn(vets);
		
		//when
		//then
		mvc.perform(get("/api/vets")
			.contentType(MediaType.APPLICATION_JSON)
			.with(jwt()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id", is(vet.getId())));
		verify(vetService).getVets();
		
	}
	
	@Test
	@DisplayName("getVets() -> given no JWT will return 401")
	void getVets_canReturn401WithNoJWT() throws Exception {
		
		//when
		//then
		mvc.perform(get("/api/vets")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());
		verifyNoInteractions(vetService);
		
	}

}
