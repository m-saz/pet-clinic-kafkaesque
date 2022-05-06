package no.group.petclinic.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;
import no.group.petclinic.entity.Pet;
import no.group.petclinic.service.OwnerService;

@WebMvcTest(OwnerController.class)
class OwnerControllerTest {
	
	@MockBean
	private OwnerService ownerService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("getOwners() -> given Owners will return json array")
	void canReturnOwnersJsonArray() throws Exception {
		
		//given
		OwnerSlim owner = new OwnerSlim(3, "Paul", "Subject", "phone", "email");
		List<OwnerSlim> allOwners = List.of(owner);
		
		when(ownerService.getOwners()).thenReturn(allOwners);
		
		//when
		//then
		mockMvc.perform(get("/api/owners")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].firstName", is(owner.getFirstName())));
		verify(ownerService, times(1)).getOwners();
	}
	
	@Test
	@DisplayName("searchOwners() -> given Owners and keyword will return json array")
	void canFindAndReturnOwnersJsonArray() throws Exception {
		
		//given
		OwnerSlim owner = new OwnerSlim(12, "Test", "Smth", "phone", "email");
		List<OwnerSlim> foundOwners = List.of(owner);
		String keyword = "test";
		
		when(ownerService.searchOwners(keyword)).thenReturn(foundOwners);
		
		//when
		//then
		mockMvc.perform(get("/api/owners/search/"
				+ "findOwnersByFirstNameOrLastName?keyword="+keyword)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].lastName", is(owner.getLastName())));
		verify(ownerService, times(1)).searchOwners(keyword);
		
	}
	
	@Test
	@DisplayName("getOwner() -> given Owner and id will return json")
	void canFindAndReturnOwnerJson() throws Exception {
		
		//given
		Owner owner = createOwnerWithName("Test", "Smith");
		String ownerId = "5";
		
		when(ownerService.getOwner(ownerId)).thenReturn(owner);
		
		//when
		//then
		mockMvc.perform(get("/api/owners/"+ownerId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lastName", is(owner.getLastName())));
		verify(ownerService, times(1)).getOwner(ownerId);
		
	}
	
	@Test
	@DisplayName("processOwner() -> given Owner will call saveOwner()"
			+ " and return no content")
	void canSaveOwner() throws Exception {
		
		//given
		Owner owner = createOwnerWithName("Test", "Smith");
		
		//when
		//then
		mockMvc.perform(post("/api/owners")
				.content(asJsonString(owner))
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
				.andExpect(status().isNoContent());
		verify(ownerService).saveOwner(owner);
		
	}
	
	@Test
	@DisplayName("updateOnwer() -> given Owner and id will call updateOwner() and "
			+ "return no content")
	void canUpdateOwner() throws Exception {
		
		//given
		Owner owner = createOwnerWithName("Test", "Smith");
		String ownerId = "16";
		
		//when
		//then
		mockMvc.perform(put("/api/owners/"+ownerId)
				.content(asJsonString(owner))
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf()))
				.andExpect(status().isNoContent());
		verify(ownerService).updateOwner(ownerId, owner);
		
	}
	
	@Test
	@DisplayName("deleteOwner() -> given id will call deleteOwner() "
			+ "and return no content")
	void canDeleteOwner() throws Exception {
		
		//given
		String ownerId = "27";
		
		//when
		//then
		mockMvc.perform(delete("/api/owners/"+ownerId)
				.with(csrf()))
				.andExpect(status().isNoContent());
		verify(ownerService).deleteOwner(ownerId);
		
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
