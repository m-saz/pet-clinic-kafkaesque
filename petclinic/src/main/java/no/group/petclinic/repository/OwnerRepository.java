package no.group.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import no.group.petclinic.dto.OwnerSlim;
import no.group.petclinic.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	
	@Query("SELECT new no.group.petclinic.dto.OwnerSlim("
			+ "o.id, o.firstName, o.lastName, o.phoneNumber, o.email) "
			+ "FROM Owner o ORDER BY o.lastName")
	List<OwnerSlim> findAllOwners();
	
	@Query("SELECT new no.group.petclinic.dto.OwnerSlim("
			+ "o.id, o.firstName, o.lastName, o.phoneNumber, o.email) "
			+ "FROM Owner o WHERE lower(o.firstName) LIKE lower(concat('%',:keyword,'%')) "
			+ "OR lower(o.lastName) LIKE lower(concat('%',:keyword,'%')) "
			+ "ORDER BY o.lastName")
	List<OwnerSlim> findOwnersByFirstNameOrLastName(@Param("keyword") String keyword);
}
