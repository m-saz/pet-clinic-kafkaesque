package no.group.petclinic.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import no.group.petclinic.dto.Type;
import no.group.petclinic.service.KafkaTypeService;

@WebMvcTest(TypeController.class)
class TypeControllerTest {

	@MockBean
	private KafkaTypeService typeService;
	
	@MockBean
	private JwtDecoder jwtDecoder;
	
	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("getTypes() -> given types and JWT will return json array")
	void getTypes_canReturnTypesJsonArray() throws Exception {
		
		//given
		Type type = new Type();
		type.setId(13);
		type.setType("test");
		List<Type> types = List.of(type);
		
		when(typeService.getTypes()).thenReturn(types);
		
		//when
		//then
		mvc.perform(get("/api/types")
			.contentType(MediaType.APPLICATION_JSON)
			.with(jwt()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].type", is(type.getType())));
		verify(typeService, times(1)).getTypes();
	}
	
	@Test
	@DisplayName("getTypes() -> given no JWT will return 401")
	void getTypes_canReturn401WithNoJWT() throws Exception {
		
		//when
		//then
		mvc.perform(get("/api/types")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized());
		verifyNoInteractions(typeService);
	}

}
