package demo.PetCarePro.services.mapper;

import java.util.ArrayList;
import java.util.List;

import demo.PetCarePro.persistence.entities.Cliente;
import demo.PetCarePro.persistence.entities.Mascota;
import demo.PetCarePro.services.dto.ClienteDTO;
import demo.PetCarePro.services.dto.MascotaDTO;

public class ClienteMapper {

	   public static ClienteDTO toDTO(Cliente cliente) {
	        ClienteDTO dto = new ClienteDTO();
	        dto.setId_cliente(cliente.getId());
	        dto.setDni(cliente.getDni());
	        dto.setNombre(cliente.getNombre());
	        dto.setEmail(cliente.getEmail());
	        dto.setTelefono(cliente.getTelefono());
	        dto.setDireccion(cliente.getDireccion());
	        
	    
	        List<MascotaDTO> mascotaDTOs = new ArrayList<>();
	        if(cliente.getMascotas() != null) {
	            for (Mascota mascota : cliente.getMascotas()) {
	                mascotaDTOs.add(MascotaMapper.toDTO(mascota));
	            }
	        }
	        dto.setMascotas(mascotaDTOs);
	        
	        return dto;
	    }
}
