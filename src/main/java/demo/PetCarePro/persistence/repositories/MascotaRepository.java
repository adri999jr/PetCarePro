package demo.PetCarePro.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import demo.PetCarePro.persistence.entities.Mascota;

public interface MascotaRepository  extends ListCrudRepository<Mascota, Integer> {
	  Optional<List<Mascota>> findByClienteUsername(String username);
	  Optional<List<Mascota>> findByNombreContainingIgnoreCase(String nombre);

}
