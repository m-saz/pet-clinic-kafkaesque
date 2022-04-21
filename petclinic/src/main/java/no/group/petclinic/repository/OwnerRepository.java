package no.group.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.group.petclinic.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

}
