package demo.PetCarePro.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import demo.PetCarePro.persistence.entities.Veterinario;

public interface VeterinarioRepository  extends ListCrudRepository<Veterinario, Integer> {
	Veterinario findByUsername(String username);
	List<Veterinario> findByValidadoFalse();

}
