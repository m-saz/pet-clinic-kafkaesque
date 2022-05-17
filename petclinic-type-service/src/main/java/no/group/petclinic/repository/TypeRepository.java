package no.group.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.group.petclinic.entity.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

}
