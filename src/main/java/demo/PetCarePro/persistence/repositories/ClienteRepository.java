package demo.PetCarePro.persistence.repositories;



import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import demo.PetCarePro.persistence.entities.Cliente;
import demo.PetCarePro.persistence.entities.Veterinario;

public interface ClienteRepository  extends ListCrudRepository<Cliente, Integer> {
	Optional<Cliente> findByUsername(String username);
}
