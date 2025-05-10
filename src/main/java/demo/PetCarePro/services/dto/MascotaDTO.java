package demo.PetCarePro.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MascotaDTO {

	  private Integer id_mascota;
	    private String nombre;
	    private String especie;
	    private String raza;
	    private String fecha_nacimiento;
	    private Integer id_cliente;
}
