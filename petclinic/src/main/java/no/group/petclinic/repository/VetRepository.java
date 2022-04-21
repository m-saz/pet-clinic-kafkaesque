package no.group.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.group.petclinic.entity.Vet;

@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {

}
