package demo.PetCarePro.services.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

	private Integer id_cliente;
	private String dni;
	private String nombre;
	private String email;
	private String telefono;
	private String direccion;
    private List<MascotaDTO> mascotas;
}
