package demo.PetCarePro.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;

import demo.PetCarePro.persistence.entities.Cliente;

public interface ClienteRepository  extends ListCrudRepository<Cliente, Integer> {

}
