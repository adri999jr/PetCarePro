package demo.PetCarePro.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;
import demo.PetCarePro.persistence.entities.Veterinario;

public interface VeterinarioRepository  extends ListCrudRepository<Veterinario, Integer> {

}
