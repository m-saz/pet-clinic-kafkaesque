package no.group.petclinic.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;
import no.group.petclinic.service.KafkaOwnerService;

@WebMvcTest(OwnerController.class)
class OwnerControllerTest {
	
	@MockBean
	private KafkaOwnerService ownerService;
	
	@MockBean
	private JwtDecoder jwtDecoder;
	
	@Autowired
	private MockMvc mockMvc;
//	
//	@Nested
//	@DisplayName("getOwners() -> ")
//	class GetOwnersTest{
//		
//		@Test
//		@DisplayName("given Owners will return json array")
//		void getOwners_canReturnOwnersJsonArray() throws Exception {
//			
//			//given
//			OwnerSlim owner = new OwnerSlim(3, "Paul", "Subject", "phone", "email");
//			List<OwnerSlim> owners = List.of(owner);
//			Pageable pageable = PageRequest.of(0, 10);
//			Page<OwnerSlim> ownersPage = new PageImpl<>(owners, pageable, owners.size());
//			
//			when(ownerService.getOwners(pageable)).thenReturn(ownersPage);
//			
//			//when
//			//then
//			mockMvc.perform(get("/api/owners")
//					.contentType(MediaType.APPLICATION_JSON)
//					.with(jwt()))
//					.andExpect(status().isOk())
//					.andExpect(jsonPath("$._embedded.owners", hasSize(1)))
//					.andExpect(jsonPath("$._embedded.owners[0].firstName",
//										is(owner.getFirstName())));
//			verify(ownerService, times(1)).getOwners(pageable);
//		}
//		
//		@Test
//		@DisplayName("given no JWT will return 401")
//		void getOwners_canReturn401WithNoJWT() throws Exception {
//			
//			//when
//			//then
//			mockMvc.perform(get("/api/owners")
//					.contentType(MediaType.APPLICATION_JSON))
//					.andExpect(status().isUnauthorized());
//			verifyNoInteractions(ownerService);
//		}
//	}
//	
//	@Nested
//	@DisplayName("searchOwners() -> ")
//	class SearchOwnersTest{
//		
//		@Test
//		@DisplayName("given Owners and keyword will return json array")
//		void searchOwners_canFindAndReturnOwnersJsonArray() throws Exception {
//			
//			//given
//			OwnerSlim owner = new OwnerSlim(12, "Test", "Smth", "phone", "email");
//			List<OwnerSlim> foundOwners = List.of(owner);
//			Pageable pageable = PageRequest.of(0, 10);
//			Page<OwnerSlim> foundOwnersPage = 
//									new PageImpl<>(foundOwners, pageable, foundOwners.size());
//			String keyword = "test";
//			
//			when(ownerService.getOwners(pageable, keyword)).thenReturn(foundOwnersPage);
//			
//			//when
//			//then
//			mockMvc.perform(get("/api/owners/search/"
//					+ "findOwnersByFirstNameOrLastName?keyword="+keyword)
//					.contentType(MediaType.APPLICATION_JSON)
//					.with(jwt()))
//					.andExpect(status().isOk())
//					.andExpect(jsonPath("$._embedded.owners", hasSize(1)))
//					.andExpect(jsonPath("$._embedded.owners[0].lastName",
//										is(owner.getLastName())));
//			verify(ownerService, times(1)).getOwners(pageable, keyword);
//			
//		}
//		
//		@Test
//		@DisplayName("given no JWT will return 401")
//		void searchOwners_canReturn401WithNoJWT() throws Exception {
//			
//			//when
//			//then
//			mockMvc.perform(get("/api/owners/search/"
//					+ "findOwnersByFirstNameOrLastName?keyword=")
//					.contentType(MediaType.APPLICATION_JSON))
//					.andExpect(status().isUnauthorized());
//			verifyNoInteractions(ownerService);
//			
//		}
//	}
//	
	@Nested
	@DisplayName("getOwner() -> ")
	class GetOwnerTest{
		
		@Test
		@DisplayName("given Owner and id will return json")
		void getOwner_canFindAndReturnOwnerJson() throws Exception {
			
			//given
			Owner owner = createOwnerWithName("Test", "Smith");
			Integer ownerId = 5;
			
			when(ownerService.getOwner(ownerId)).thenReturn(owner);
			
			//when
			//then
			mockMvc.perform(get("/api/owners/"+ownerId)
					.contentType(MediaType.APPLICATION_JSON)
					.with(jwt()))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.lastName", is(owner.getLastName())));
			verify(ownerService, times(1)).getOwner(ownerId);
			
		}
		
		@Test
		@DisplayName("given id and no JWT will return 401")
		void getOwner_canReturn401WithNoJWT() throws Exception {
			
			//given
			Integer ownerId = 11;
			
			//when
			//then
			mockMvc.perform(get("/api/owners/"+ownerId)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isUnauthorized());
			verifyNoInteractions(ownerService);
			
		}
	}
	
	@Nested
	@DisplayName("processOwner() -> ")
	class ProcessOwnerTest{
		
		@Test
		@DisplayName("given Owner will call saveOwner()"
				+ " and return no content")
		void processOwner_canSaveOwner() throws Exception {
			
			//given
			Owner owner = createOwnerWithName("Test", "Smith");
			
			//when
			//then
			mockMvc.perform(post("/api/owners")
					.content(asJsonString(owner))
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf())
					.with(jwt()))
					.andExpect(status().isNoContent());
			verify(ownerService).saveOwner(owner);
			
		}
		
		@Test
		@DisplayName("given Owner and no JWT will return 401")
		void processOwner_canRetrun401WithNoJWT() throws Exception {
			
			//given
			Owner owner = createOwnerWithName("Test", "Smith");
			
			//when
			//then
			mockMvc.perform(post("/api/owners")
					.content(asJsonString(owner))
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isUnauthorized());
			verifyNoInteractions(ownerService);
			
		}
	}
	
	
	@Nested
	@DisplayName("updateOwner() -> ")
	class UpdateOwnerTest{
		
		@Test
		@DisplayName("given Owner, id and JWT will call updateOwner() and "
				+ "return no content")
		void updateOwner_canUpdateOwner() throws Exception {
			
			//given
			Owner owner = createOwnerWithName("Test", "Smith");
			Integer ownerId = 16;
			
			//when
			//then
			mockMvc.perform(put("/api/owners/"+ownerId)
					.content(asJsonString(owner))
					.contentType(MediaType.APPLICATION_JSON)
					.with(jwt()))
					.andExpect(status().isNoContent());
			verify(ownerService).updateOwner(ownerId, owner);
			
		}
		
		@Test
		@DisplayName("given no JWT will return 401")
		void updateOwner_canReturn401WithNoJWT() throws Exception {
			
			//given
			Owner owner = createOwnerWithName("Test", "Smith");
			Integer ownerId = 27;
			
			//when
			//then
			mockMvc.perform(put("/api/owners/"+ownerId)
					.content(asJsonString(owner))
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isUnauthorized());
			verifyNoInteractions(ownerService);
			
		}
	}
	
	@Nested
	@DisplayName("deleteOwner() -> ")
	class DeleteOwnerTest {
		
		@Test
		@DisplayName("given id and JWT will call deleteOwner() "
				+ "and return no content")
		void deleteOwner_canDeleteOwner() throws Exception {
			
			//given
			Integer ownerId = 42;
			
			//when
			//then
			mockMvc.perform(delete("/api/owners/"+ownerId)
					.with(jwt()))
					.andExpect(status().isNoContent());
			verify(ownerService).deleteOwner(ownerId);
			
		}
		
		@Test
		@DisplayName("given id and no JWT will return 401")
		void deleteOwner_canReturn401WithNoJWT() throws Exception {
			
			//given
			Integer ownerId = 66;
			
			//when
			//then
			mockMvc.perform(delete("/api/owners/"+ownerId))
					.andExpect(status().isUnauthorized());
			verifyNoInteractions(ownerService);
			
		}
	}

	private Owner createOwnerWithName(String firstName, String lastName) {
		
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setPhoneNumber("number");
		owner.setEmail("email");
		owner.setCity("Somewhere");
		owner.setAddress("Anywhere");
		owner.setPets(new HashSet<Pet>());
		return owner;
	}
	
	private String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	} 
}
