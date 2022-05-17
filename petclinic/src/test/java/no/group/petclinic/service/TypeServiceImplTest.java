//package no.group.petclinic.service;
//
//import static org.mockito.Mockito.verify;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import no.group.petclinic.repository.TypeRepository;
//
//@ExtendWith(MockitoExtension.class)
//class TypeServiceImplTest {
//
//	@Mock
//	private TypeRepository typeRepository; 
//	
//	private TypeServiceImpl underTest;
//	
//	@BeforeEach
//	void setup() {
//		underTest = new TypeServiceImpl(typeRepository);
//	}
//	
//	@Test
//	@DisplayName("getTypes() -> will call findAll()")
//	void testGetTypes() {
//		
//		//when
//		underTest.getTypes();
//		
//		//then
//		verify(typeRepository).findAll();
//	}
//
//}
