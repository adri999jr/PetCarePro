package demo.PetCarePro.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;

import demo.PetCarePro.persistence.entities.Mascota;

public interface MascotaRepository  extends ListCrudRepository<Mascota, Integer> {

}
