package demo.PetCarePro.services.mapper;

import demo.PetCarePro.persistence.entities.Mascota;
import demo.PetCarePro.services.dto.MascotaDTO;

public class MascotaMapper {
	public static MascotaDTO toDTO(Mascota mascota) {
	    MascotaDTO dto = new MascotaDTO();
	    dto.setId_mascota(mascota.getId_mascota());
	    dto.setNombre(mascota.getNombre());
	    dto.setEspecie(mascota.getEspecie().name());
	    dto.setRaza(mascota.getRaza());
	    dto.setHistorial_medico(mascota.getHistorial_medico());
	    dto.setFecha_nacimiento(mascota.getFecha_nacimiento());

	    if (mascota.getCliente() != null) {
	        dto.setId_cliente(mascota.getCliente().getId());
	    } else {
	        // Opción: asumiendo que si el objeto cliente es nulo, 
	        // se puede asignar el valor del campo idCliente directamente
	        dto.setId_cliente(mascota.getIdCliente());
	        // O lo puedes dejar como null:
	        // dto.setId_cliente(null);
	    }
	    
	    return dto;
	}

}
