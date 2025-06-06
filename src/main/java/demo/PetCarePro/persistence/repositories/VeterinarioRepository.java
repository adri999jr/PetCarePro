package demo.PetCarePro.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import demo.PetCarePro.persistence.entities.Veterinario;

public interface VeterinarioRepository  extends ListCrudRepository<Veterinario, Integer> {
	Optional<Veterinario> findByUsername(String username);
	List<Veterinario> findByRoleNot(String role);
	List<Veterinario> findByValidadoFalse();
	@Query(value = "SELECT * FROM veterinario ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Optional<Veterinario> findAleatorio();


	

}
