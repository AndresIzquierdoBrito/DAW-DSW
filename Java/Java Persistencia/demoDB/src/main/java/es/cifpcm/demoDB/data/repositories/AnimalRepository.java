package es.cifpcm.demoDB.data.repositories;

import es.cifpcm.demoDB.controllers.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnimalRepository extends JpaRepository<Animal, Integer>, JpaSpecificationExecutor<Animal> {

}